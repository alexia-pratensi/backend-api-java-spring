package fr.alexia.backendapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import fr.alexia.backendapi.DTO.AuthResponse;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.DTO.LoginRequest;
import fr.alexia.backendapi.model.InternalUser;

public interface UserService {

    UserDetails registerUser(InternalUser user);

    boolean existsByEmail(String email);

    AuthResponse loginUser(LoginRequest loginRequest);

    InternalUserDTO getCurrentUser();

}
