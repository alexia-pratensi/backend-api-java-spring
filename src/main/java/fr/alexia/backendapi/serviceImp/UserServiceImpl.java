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

    private InternalUserDTO convertToDTO(InternalUser user) {
        return modelMapper.map(user, InternalUserDTO.class);
    }

    public InternalUserDTO getUserById(Long id) {
        Optional<InternalUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return convertToDTO(user.get());
        }
        return null;
    }

    public boolean existsByName(String username) {
        return userRepository.existsByName(username);
    }

    public UserDetails registerUser(InternalUser user) {

        if (existsByName(user.getEmail())) {
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
