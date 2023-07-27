package fr.alexia.backendapi.configuration;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
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
public class SecurityConfig {

	@Autowired
	private JwtTokenFilter jwtTokenFilter;

	/**
	 * Configure the security filter chain.
	 * 
	 * @param http The HttpSecurity object to configure.
	 * @return The configured SecurityFilterChain object.
	 * @throws Exception If an error occurs during configuration.
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults()) // Enable Cross-Origin Resource Sharing (CORS) with default settings.
				.csrf(csrf -> csrf.disable()) // Disable CSRF protection.
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Set
																												// session
																												// creation
																												// policy
																												// to
																												// STATELESS,
																												// meaning
																												// no
																												// session
																												// will
																												// be
																												// created
																												// or
																												// used.
				.exceptionHandling(handler -> handler.authenticationEntryPoint((request, response, ex) -> {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()); // Handle unauthorized
																								// access by sending 401
																								// Unauthorized status
																								// code.
				}))

				.authorizeHttpRequests(authorize -> {
					authorize
							.requestMatchers("/api/auth/login", "/api/auth/register").permitAll() // Allow access to
																									// login and
																									// register
																									// endpoints without
																									// authentication.
							.requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/documentation.html",
									"/v3/api-docs/**") // Allow access to Swagger UI and documentation endpoints without
														// authentication.
							.permitAll()
							.anyRequest().authenticated(); // Require authentication for all other requests.
				})

				.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class); // Add JWT token filter
																								// before
																								// UsernamePasswordAuthenticationFilter.

		return http.build();
	}

	/**
	 * Creates a CorsFilter bean to configure CORS settings.
	 *
	 * @return The configured CorsFilter.
	 */
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true); // Allow credentials (e.g., cookies) to be sent in the CORS request.
		config.setAllowedOrigins(Arrays.asList("http://localhost:4200")); // Allow requests from the specified origin.
		config.addAllowedHeader("*"); // Allow all headers to be sent in the request.
		config.addAllowedMethod("*");// Allow all HTTP methods (e.g., GET, POST, etc.).
		source.registerCorsConfiguration("/**", config); // Apply the CORS configuration to all paths.
		return new CorsFilter(source); // Create and return the CorsFilter.
	}

	/**
	 * Creates a PasswordEncoder bean to encode passwords.
	 *
	 * @return The PasswordEncoder bean.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Use BCryptPasswordEncoder for password encoding.
	}

	/**
	 * Creates an AuthenticationManager bean.
	 *
	 * @param http                  The HttpSecurity object.
	 * @param bCryptPasswordEncoder The BCryptPasswordEncoder.
	 * @param userDetailService     The CustomUserDetailsService.
	 * @return The AuthenticationManager bean.
	 * @throws Exception If an error occurs during configuration.
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
			UserDetailsService userDetailService) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailService) // Set the userDetailsService to be used for authentication.
				.passwordEncoder(bCryptPasswordEncoder) // Set the passwordEncoder to be used for authentication.
				.and().build();
	}

}
