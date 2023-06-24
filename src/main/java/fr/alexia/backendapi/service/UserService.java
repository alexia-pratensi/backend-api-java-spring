package fr.alexia.backendapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.IUserRepository;

@Service
public class UserService {
	
	@Autowired
    private IUserRepository userRepository;
	
	public Optional<InternalUser> getUser(final Long id) {
        return userRepository.findById(id);
    }

}
