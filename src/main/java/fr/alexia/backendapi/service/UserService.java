package fr.alexia.backendapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.User;
import fr.alexia.backendapi.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
    private UserRepository userRepository;
	
	public Optional<User> getUser(final Long id) {
        return userRepository.findById(id);
    }

}
