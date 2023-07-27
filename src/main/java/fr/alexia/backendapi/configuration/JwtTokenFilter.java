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
public class JwtTokenFilter extends OncePerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(JwtTokenFilter.class);

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	/**
	 * Filter method to perform JWT token validation and authentication.
	 *
	 * @param request     The HTTP request.
	 * @param response    The HTTP response.
	 * @param filterChain The filter chain to execute.
	 * @throws ServletException If an internal error occurs during filtering.
	 * @throws IOException      If an I/O error occurs during filtering.
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.info("Enter doFilterInternal JwtTokenFilter");

		// Get authorization header and validate
		final String header = request.getHeader(HttpHeaders.AUTHORIZATION.toLowerCase());
		if (header == null || header.isEmpty() || !header.startsWith("Bearer ")) {
			// If the authorization header is missing or does not start with "Bearer ", skip
			// this filter and proceed to the next one
			filterChain.doFilter(request, response);
			return;
		}

		// Get jwt token and validate
		final String token = header.split(" ")[1].trim();

		logger.info("Token received by filter is : " + token);

		if (!jwtTokenUtil.validate(token)) {
			// If the JWT token is not valid, skip this filter and proceed to the next one
			filterChain.doFilter(request, response);
			return;
		}

		// Get user identity and set it on the spring security context
		UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsername(token));

		// Create an authentication token with the user details and set it on the
		// SecurityContextHolder
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails == null ? List.of() : userDetails.getAuthorities());

		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		// Continue with the filter chain
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

}
