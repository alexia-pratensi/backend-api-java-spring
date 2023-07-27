package fr.alexia.backendapi.serviceImp;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.alexia.backendapi.DTO.AuthResponse;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.DTO.LoginRequest;
import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Convert an InternalUser entity to an InternalUserDTO data transfer object.
     *
     * @param user The InternalUser entity to convert.
     * @return The converted InternalUserDTO data transfer object.
     */
    private InternalUserDTO convertToDTO(InternalUser user) {
        return modelMapper.map(user, InternalUserDTO.class);
    }

    /**
     * Check if a user with the given email already exists.
     *
     * @param email The email address of the user to check.
     * @return true if a user with the given email exists, false
     *         otherwise.
     */
    public boolean existsByEmail(String name) {
        return userRepository.existsByEmail(name);
    }

    /**
     * Register a new user.
     *
     * @param user The InternalUser object representing the new user to register.
     * @return The UserDetails object representing the registered user.
     * @throws IllegalArgumentException if a user with the same email already
     *                                  exists.
     */
    public UserDetails registerUser(InternalUser user) {

        if (existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);
        user.setName(user.getEmail());
        user.setEmail(user.getEmail());
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());

        userRepository.save(user);

        UserDetails userDetails = new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList());

        return userDetails;
    }

    /**
     * Authenticate a user and generate an access token upon successful
     * authentication.
     *
     * @param loginRequest The LoginRequest object containing the user's email and
     *                     password.
     * @return The AuthResponse containing the generated access token upon
     *         successful authentication.
     * @throws BadCredentialsException if the authentication fails (invalid
     *                                 credentials).
     */
    public AuthResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (authenticate.isAuthenticated()) {
                User authenticatedUser = (User) authenticate.getPrincipal();
                String token = jwtTokenUtil.generateAccessToken(authenticatedUser);

                AuthResponse authResponse = new AuthResponse(token);
                return authResponse;
            } else {
                throw new BadCredentialsException("Invalid credentials");
            }

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException("Invalid credentials");
        }

    }

    /**
     * Get the currently authenticated user.
     *
     * @return The InternalUserDTO representing the currently authenticated user, or
     *         null if not authenticated.
     */
    @Override
    public InternalUserDTO getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String name = authentication.getName();

        Optional<InternalUser> userOptional = userRepository.findByName(name);
        if (userOptional.isPresent()) {
            InternalUser user = userOptional.get();
            return convertToDTO(user);
        }
        return null;
    }

}
