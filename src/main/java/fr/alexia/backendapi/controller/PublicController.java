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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.service.UserService;
import jakarta.validation.Valid;

//permet à un utilisateur de se connecter en fournissant un nom d'utilisateur et un mot de passe valides.
@RestController
@RequestMapping("api/auth")
public class PublicController {
	
	//authentifier les informations d'identification fournies
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(PublicController.class);

	//permet à un utilisateur de se connecter en fournissant un nom d'utilisateur et un mot de passe valides
	@PostMapping("/login")
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
	
	//permet à un utilisateur de s'inscrire en fournissant les informations d'identification
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody @Valid InternalUser user) {
	    try {
	        // Vérifier si l'utilisateur existe déjà
	        if (userService.existsByUsername(user.getUsername())) {
	            return ResponseEntity.badRequest().body("Username already exists");
	        }

	        // Créer un nouvel utilisateur
	        User registeredUser = (User) userService.registerUser(user);

	        // Générer le token d'accès
	        String token = jwtTokenUtil.generateAccessToken(registeredUser);
	        logger.info("Token is: " + token);

	        return ResponseEntity.ok()
	                .header(HttpHeaders.AUTHORIZATION, token)
	                .body(registeredUser.getUsername() + " successfully registered");
	    } catch (Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	// renvoie les informations de l'utilisateur connecté.
	@RequestMapping("/me")
    public ResponseEntity<User> me() {
		//utilisé pour obtenir l'objet Authentication représentant l'utilisateur connecté
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // le nom d'utilisateur est extrait

        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}