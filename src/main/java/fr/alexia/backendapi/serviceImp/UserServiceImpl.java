package fr.alexia.backendapi.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.UserService;
import io.jsonwebtoken.lang.Collections;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean existsByUsername(String username) {
        return userService.existsByUsername(username);
    }

    public UserDetails registerUser(InternalUser user) {
        // Vérifier si l'utilisateur existe déjà
        if (existsByUsername(user.getName())) {
            throw new IllegalArgumentException("Username already exists");
        }

        // Encoder le mot de passe avant de l'enregistrer
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // Enregistrer l'utilisateur
        userRepository.save(user);

        // Convertir InternalUser en UserDetails de
        // org.springframework.security.core.userdetails.User
        UserDetails userDetails = new User(
                user.getName(),
                user.getPassword(),
                Collections.emptyList());

        return userDetails;
    }

    public User findByName(String name) {
        return (User) userService.findByUsername(name);
    }
}

// @Autowired
// private UserRepository userRepository;
// @Autowired
// private ModelMapper modelMapper;

// @Override
// public InternalUserDTO getUserById(Long id) {
// InternalUser user = userRepository.findById(id).get();
// return modelMapper.map(user, InternalUserDTO.class);
// }

// @Override
// public InternalUserDTO createUser(InternalUserDTO userDTO) {
// InternalUser user = modelMapper.map(userDTO, InternalUser.class);
// user = userRepository.save(user);
// return modelMapper.map(user, InternalUserDTO.class);
// }

// @Override
// public List<InternalUser> findByUsername(String username) {
// return userRepository.findByUsername(username);
// }

// @Override
// public boolean existsByUsername(String username) {
// return userRepository.existsByUsername(username);
// }

// @Autowired
// private UserRepository userRepository;
// @Autowired
// private ModelMapper modelMapper;

// @Override
// public InternalUserDTO getUserById(Long id) {
// // InternalUser user = userRepository.findById(id)
// // .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

// // return modelMapper.map(user, InternalUserDTO.class);
// InternalUser user = userRepository.findById(id).orElse(null);
// if (user != null) {
// return convertToDTO(user);
// }
// return null;
// }

// @Override
// public InternalUserDTO createUser(InternalUserDTO userDTO) {
// InternalUser user = convertToEntity(userDTO);
// InternalUser savedUser = userRepository.save(user);
// return convertToDTO(savedUser);
// }

// private InternalUserDTO convertToDTO(InternalUser user) {
// return modelMapper.map(user, InternalUserDTO.class);
// }

// private InternalUser convertToEntity(InternalUserDTO userDTO) {
// return modelMapper.map(userDTO, InternalUser.class);
// }