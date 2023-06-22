package fr.alexia.backendapi.service;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;

@Service
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public UserDetails registerUser(InternalUser user) {
        // Vérifier si l'utilisateur existe déjà
        if (existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encoder le mot de passe avant de l'enregistrer
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Enregistrer l'utilisateur
        userRepository.save(user);

        // Convertir InternalUser en UserDetails de org.springframework.security.core.userdetails.User
        UserDetails userDetails = new User(
            user.getUsername(),
            user.getPassword(),
            Collections.emptyList()
        );

        return userDetails;
    }

    public User findByUsername(String username) {
        return (User) userRepository.findByUsername(username);
    }
}
