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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // @Override
    // public InternalUserDTO getUserById(Long id) {
    // InternalUser user = userRepository.findById(id).orElse(null);
    // if (user != null) {
    // return convertToDTO(user);
    // }
    // return null;
    // }

    private InternalUserDTO convertToDTO(InternalUser user) {
        return modelMapper.map(user, InternalUserDTO.class);
    }

    public boolean existsByName(String username) {
        return userRepository.existsByName(username);
    }

    public UserDetails registerUser(InternalUser user) {
        // Vérifier si l'utilisateur existe déjà
        if (existsByName(user.getEmail())) {
            throw new IllegalArgumentException("email already exists");
        }

        // Encoder le mot de passe avant de l'enregistrer
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setName(user.getEmail());
        user.setEmail(user.getEmail());
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());

        // Enregistrer l'utilisateur
        userRepository.save(user);

        UserDetails userDetails = new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList());

        return userDetails;
    }

    public AuthResponse loginUser(LoginRequest loginRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            if (authenticate.isAuthenticated()) {
                // L'authentification est réussie, vous pouvez maintenant générer le token JWT
                User authenticatedUser = (User) authenticate.getPrincipal();
                String token = jwtTokenUtil.generateAccessToken(authenticatedUser);

                // Créer et renvoyer l'AuthResponse avec le token
                AuthResponse authResponse = new AuthResponse(token);
                return authResponse;
            } else {
                // Gérer l'échec de l'authentification si nécessaire
                throw new BadCredentialsException("Invalid credentials");
            }
        } catch (AuthenticationException ex) {
            // Gérer l'échec de l'authentification si nécessaire
            throw new BadCredentialsException("Invalid credentials");
        }
        // try {
        // String encodedPassword = new
        // BCryptPasswordEncoder().encode(loginRequest.getPassword());
        // Authentication authenticate = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
        // encodedPassword));

        // User authenticatedUser = (User) authenticate.getPrincipal();

        // String token = jwtTokenUtil.generateAccessToken(authenticatedUser);

        // // Create and return the AuthResponse with the token
        // AuthResponse authResponse = new AuthResponse(token);

        // return authResponse;
        // } catch (AuthenticationException ex) {
        // // Handle authentication failure here, if needed
        // throw new BadCredentialsException("Invalid credentials");
        // }
    }

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
