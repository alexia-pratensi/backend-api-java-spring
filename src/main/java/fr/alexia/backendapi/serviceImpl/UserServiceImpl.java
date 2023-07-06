package fr.alexia.backendapi.serviceImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.mapper.UserMapper;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.UserNotFoundException;
import fr.alexia.backendapi.modelDTO.InternalUserDTO;
import fr.alexia.backendapi.repository.IUserRepository;
import fr.alexia.backendapi.service.UserService;
//import fr.alexia.backendapi.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private JwtUtil jwtUtil;

    @Override
    public InternalUserDTO registerUser(InternalUserDTO userDTO) {
        InternalUser user = userMapper.mapToInternalUser(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        InternalUser savedUser = userRepository.save(user);
        return userMapper.mapToInternalUserDTO(savedUser);
    }

    @Override
    public String loginUser(String token) {
        // TODO: Implement login logic using the provided token
        // You can use the jwtUtil to validate and extract information from the token
        // Return an appropriate response based on the login result
        return null;
    }

    @Override
    public InternalUserDTO getLoggedInUser(String token) {
        // TODO: Implement logic to get the logged-in user using the provided token
        // You can use the jwtUtil to validate and extract information from the token
        // Return the DTO representation of the logged-in user
        return null;
    }
    
    @Override
    public InternalUserDTO getUserById(Long userId) {
        Optional<InternalUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            InternalUser user = optionalUser.get();
            return UserMapper.MAPPER.mapToInternalUserDTO(user);
        } else {
            throw new UserNotFoundException("User not found");
        }
    }



    
}
