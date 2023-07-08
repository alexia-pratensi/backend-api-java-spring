package fr.alexia.backendapi.controller;

//import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
    private UserService userService;
    
    @GetMapping("/me")
    public ResponseEntity<InternalUserDTO> getUserById(@PathVariable Long id) {
    	InternalUserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }
    
    @PostMapping("/register")
    public ResponseEntity<InternalUserDTO> createUser(@RequestBody InternalUserDTO userDTO) {
    	InternalUserDTO createdUserDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }
    
    
//    @GetMapping
//    public ResponseEntity<List<InternalUserDTO>> getAllUsers() {
//        List<InternalUserDTO> userDTOs = userService.getAllUsers();
//        return ResponseEntity.ok(userDTOs);
//    }
    
//    @PutMapping("/{id}")
//    public ResponseEntity<InternalUserDTO> updateUser(@PathVariable Long id, @RequestBody InternalUserDTO userDTO) {
//    	InternalUserDTO updatedUserDTO = userService.updateUser(id, userDTO);
//        return ResponseEntity.ok(updatedUserDTO);
//    }
    
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}

