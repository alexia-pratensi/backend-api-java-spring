package fr.alexia.backendapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.DTO.LoginRequest;
import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.exceptions.ErrorResponse;
import fr.alexia.backendapi.exceptions.ApiException.BadRequestException;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.service.UserService;
import fr.alexia.backendapi.serviceImp.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import fr.alexia.backendapi.DTO.AuthResponse;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private UserService userService;

    /**
     * Register a new user
     *
     * @param user The user object to be registered.
     * @return ResponseEntity<?> A response entity containing the registered user
     *         DTO and access token.
     */
    @Operation(summary = "Register a new user", description = "Route for registering a new user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalUserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody @Valid InternalUser user) {
        try {
            // Create a new user
            User registeredUser = (User) userServiceImpl.registerUser(user);

            // Generate the access token
            String token = jwtTokenUtil.generateAccessToken(registeredUser);
            logger.info("Token is: " + token);

            AuthResponse authResponse = new AuthResponse(token);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .body(authResponse);

        } catch (IllegalArgumentException ex) {
            // Handle invalid input errors
            logger.error("Invalid input for user registration: " + ex.getMessage(), ex);
            return ResponseEntity.badRequest().build();

        } catch (BadRequestException ex) {
            // Handle user registration related errors (e.g., bad request)
            logger.error("Bad request for user registration: " + ex.getMessage(), ex);
            ErrorResponse errorResponse = new ErrorResponse("Bad request", Arrays.asList(ex.getMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            logger.error("Error occurred while registering user: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * User login
     *
     * @param loginRequest The login request object containing user credentials.
     * @return ResponseEntity<AuthResponse> A response entity containing the
     *         authentication response.
     */
    @Operation(summary = "User login", description = "Route for user login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthResponse.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authSuccess = userServiceImpl.loginUser(loginRequest);
            return ResponseEntity.ok().body(authSuccess);

        } catch (UsernameNotFoundException ex) {
            // Handle the case where the user is not found
            logger.error("User not found: " + ex.getMessage(), ex);
            return ResponseEntity.notFound().build();

        } catch (BadCredentialsException ex) {
            // Handle incorrect credentials errors
            logger.error("Invalid credentials: " + ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            logger.error("Error occurred while fetching current user: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get current user
     *
     * @return ResponseEntity<InternalUserDTO> A response entity containing the
     *         current logged-in user DTO.
     */
    @Operation(summary = "Get current user", description = "Route for getting the current logged-in user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found current user", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalUserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/auth/me")
    public ResponseEntity<InternalUserDTO> getCurrentUser() {
        try {
            InternalUserDTO userDTO = userService.getCurrentUser();
            if (userDTO != null) {
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (UsernameNotFoundException ex) {
            // Handle the case where the user is not found
            logger.error("User not found: " + ex.getMessage(), ex);
            return ResponseEntity.notFound().build();

        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            logger.error("Error occurred while fetching current user: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Get user by ID
     *
     * @param id The ID of the user to retrieve.
     * @return ResponseEntity<InternalUserDTO> A response entity containing the user
     *         DTO if found, or an error response if not found.
     */
    @Operation(summary = "Get user by ID", description = "Route for getting a user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = InternalUserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<InternalUserDTO> getUserById(@PathVariable("id") Long id) {
        try {
            InternalUserDTO userDTO = userServiceImpl.getUserById(id);
            return ResponseEntity.ok(userDTO);
        } catch (UsernameNotFoundException ex) {
            // Handle the case where the user is not found
            logger.error("User not found: " + ex.getMessage(), ex);
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            // Handle any other unexpected exceptions
            logger.error("Error occurred while fetching user: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
