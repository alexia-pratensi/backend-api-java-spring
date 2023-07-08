package fr.alexia.backendapi.service;

import fr.alexia.backendapi.DTO.InternalUserDTO;

public interface UserService {
	
    InternalUserDTO getUserById(Long id);
    
    InternalUserDTO createUser(InternalUserDTO userDTO);
    
}
