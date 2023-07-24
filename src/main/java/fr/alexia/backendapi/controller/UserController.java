package fr.alexia.backendapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.DTO.LoginRequest;
import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.service.UserService;
import fr.alexia.backendapi.serviceImp.UserServiceImpl;
import jakarta.validation.Valid;
import fr.alexia.backendapi.DTO.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid InternalUser user) {
        try {
            // Create a new user
            User registeredUser = (User) userServiceImpl.registerUser(user);

            // Generate the acces token
            String token = jwtTokenUtil.generateAccessToken(registeredUser);
            logger.info("Token is: " + token);

            AuthResponse authResponse = new AuthResponse(token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(authResponse);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        AuthResponse authSuccess = userServiceImpl.loginUser(loginRequest);
        return ResponseEntity.ok().body(authSuccess);
    }

    @GetMapping("/me")
    public ResponseEntity<InternalUserDTO> getCurrentUser() {
        InternalUserDTO userDTO = userService.getCurrentUser();
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
