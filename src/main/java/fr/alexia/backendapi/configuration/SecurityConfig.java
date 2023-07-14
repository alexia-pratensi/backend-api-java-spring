package fr.alexia.backendapi.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@EnableJpaRepositories(basePackages = "fr.alexia.backendapi.repository")
public class SecurityConfig {

    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    // retourne un objet SecurityFilterChain. Cette méthode configure les règles de
    // sécurité pour les requêtes HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Désactivation de la protection CSRF (Cross-Site Request Forgery) avec
        // http.csrf().disable().
        http = http.cors().and().csrf().disable();
        // Configuration de la gestion des sessions en mode sans état
        http = http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        // Définition d'un point d'entrée d'authentification personnalisé pour les
        // erreurs d'authentification
        http = http.exceptionHandling().authenticationEntryPoint((request, response, ex) -> {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
        }).and();
        // Configuration des autorisations pour les différentes URL
        http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rentals").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/rentals/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/messages/").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/rentals/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/rentals/**").hasRole("ADMIN")
                .anyRequest().authenticated();
        // validation du jeton JWT dans les requêtes entrantes:
        // Ajout du filtre jwtTokenFilter (qui est un JwtTokenFilter) avant le filtre
        // UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // configure les paramètres CORS (Cross-Origin Resource Sharing) pour autoriser
    // les requêtes provenant de http://localhost:4200.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET", "POST", "PUT", "DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    // retourne un objet BCryptPasswordEncoder qui sera utilisé pour encoder les
    // mots de passe des utilisateurs.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // configure l'AuthenticationManager en utilisant le UserDetailsService, le
    // BCryptPasswordEncoder et l'objet HttpSecurity.
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
            UserDetailsService userDetailService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(bCryptPasswordEncoder)
                .and().build();
    }

    public void setJwtTokenFilter(JwtTokenFilter jwtTokenFilter) {
        this.jwtTokenFilter = jwtTokenFilter;
    }

}
