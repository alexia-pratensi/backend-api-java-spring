package fr.alexia.backendapi.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.model.InternalUser;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/public")
public class PublicController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	Logger logger = LoggerFactory.getLogger(PublicController.class);

	@PostMapping("login")
	public ResponseEntity<String> login(@RequestBody @Valid InternalUser user) {

		try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

			User autendicatedUser = (User) authenticate.getPrincipal();

			String token = jwtTokenUtil.generateAccessToken(autendicatedUser);
			logger.info("Token is : " + token);

			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(autendicatedUser.getUsername() + " successfully autenticated");

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}