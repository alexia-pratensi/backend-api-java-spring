package fr.alexia.backendapi.serviceImp;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private ModelMapper modelMapper;
  
    
    @Override
    public InternalUserDTO getUserById(Long id) {
    	InternalUser user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found with id: " + id));
        return modelMapper.map(user, InternalUserDTO.class);
    }


    @Override
    public InternalUserDTO createUser(InternalUserDTO userDTO) {
    	   InternalUser user = modelMapper.map(userDTO, InternalUser.class);
           user.setCreatedAt(LocalDateTime.now());
           user.setUpdatedAt(LocalDateTime.now());
           InternalUser createdUser = userRepository.save(user);
           return modelMapper.map(createdUser, InternalUserDTO.class);
//    	InternalUser user = modelMapper.map(userDTO, InternalUser.class);
//    	InternalUser savedUser = userRepository.save(user);
//        return modelMapper.map(savedUser, InternalUserDTO.class);
    }


}
