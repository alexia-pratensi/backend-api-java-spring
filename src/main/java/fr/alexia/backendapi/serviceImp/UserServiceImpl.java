package fr.alexia.backendapi.serviceImp;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InternalUserDTO getUserById(Long id) {
        // InternalUser user = userRepository.findById(id)
        // .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        // return modelMapper.map(user, InternalUserDTO.class);
        InternalUser user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return convertToDTO(user);
        }
        return null;
    }

    @Override
    public InternalUserDTO createUser(InternalUserDTO userDTO) {
        InternalUser user = convertToEntity(userDTO);
        InternalUser savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    private InternalUserDTO convertToDTO(InternalUser user) {
        return modelMapper.map(user, InternalUserDTO.class);
    }

    private InternalUser convertToEntity(InternalUserDTO userDTO) {
        return modelMapper.map(userDTO, InternalUser.class);
    }

}
