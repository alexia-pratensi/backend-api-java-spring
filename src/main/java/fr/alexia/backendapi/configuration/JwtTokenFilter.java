package fr.alexia.backendapi.configuration;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtTokenFilter extends OncePerRequestFilter { // le filtre ne sera exécuté qu'une seule fois pour chaque
                                                           // requête.

    private Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil; // pour valider les jetons JWT.

    private CustomUserDetailsService customUserDetailsService; // pour charger les informations utilisateur à partir du
                                                               // jeton JWT

    @Override // méthode principale du filtre
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        logger.info("Enter doFilterInternal JwtTokenFilter"); // Récupération de l'en-tête d'autorisation de la requête
                                                              // et validation.

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase()); // Extraction du jeton JWT du
                                                                                          // header
        if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();

        logger.info("Token received by filter is : " + token);

        if (!jwtTokenUtil.validate(token)) { // Validation du jeton JWT à l'aide de jwtTokenUtil.validate()
            filterChain.doFilter(request, response);
            return;
        }

        // Get user identity and set it on the spring security context
        // Chargement des informations utilisateur à partir du jeton JWT à l'aide de
        // customUserDetailsService.loadUserByUsername()
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));
        // Création d'un objet UsernamePasswordAuthenticationToken avec les informations
        // utilisateur et configuration du contexte de sécurité de Spring
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                userDetails == null ? List.of() : userDetails.getAuthorities());

        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response); // Appel à filterChain.doFilter() pour continuer le traitement de la
                                                 // requête.
    }

    public void setCustomUserDetailsService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

}