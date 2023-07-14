package fr.alexia.backendapi.controller;

//import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
//import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.configuration.JwtTokenUtil;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    public ResponseEntity<InternalUserDTO> getUserById(@PathVariable Long id) {
        InternalUserDTO userDTO = userService.getUserById(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<InternalUserDTO> createUser(@RequestBody InternalUserDTO userDTO) {
        InternalUserDTO createdUserDTO = userService.createUser(userDTO);
        return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody InternalUser user) {
    	try {
			Authentication authenticate = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword()));

			User autendicatedUser = (User) authenticate.getPrincipal();

			String token = jwtTokenUtil.generateAccessToken(autendicatedUser);
			
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, token)
					.body(token);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
    }

    // @GetMapping
    // public ResponseEntity<List<InternalUserDTO>> getAllUsers() {
    // List<InternalUserDTO> userDTOs = userService.getAllUsers();
    // return ResponseEntity.ok(userDTOs);
    // }

    // @PutMapping("/{id}")
    // public ResponseEntity<InternalUserDTO> updateUser(@PathVariable Long id,
    // @RequestBody InternalUserDTO userDTO) {
    // InternalUserDTO updatedUserDTO = userService.updateUser(id, userDTO);
    // return ResponseEntity.ok(updatedUserDTO);
    // }

    // @DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    // userService.deleteUser(id);
    // return ResponseEntity.noContent().build();
    // }
}
