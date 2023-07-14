package fr.alexia.backendapi.service;

import java.util.List;

// import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.model.InternalUser;

public interface UserService {

    // InternalUserDTO getUserById(Long id);

    // InternalUserDTO createUser(InternalUserDTO userDTO);

    List<InternalUser> findByUsername(String username);

    boolean existsByUsername(String username);

}
