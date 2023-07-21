package fr.alexia.backendapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.MappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;

import fr.alexia.backendapi.DTO.MessageDTO;
import fr.alexia.backendapi.DTO.MessageRequest;
import fr.alexia.backendapi.DTO.MessageResponse;
import fr.alexia.backendapi.service.MessageService;
import fr.alexia.backendapi.serviceImp.MessageServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class MessageController {

	@Autowired
	private MessageServiceImpl messageServiceImpl;

	@PostMapping("/messages")
	public ResponseEntity<MessageResponse> postMessage(@RequestBody MessageDTO messageDTO) {
		try {
			messageServiceImpl.postMessage(messageDTO.getRentalId(), messageDTO.getUserId(), messageDTO.getMessage());
			MessageResponse response = new MessageResponse("Message sent!");
			return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			// Handle invalid input or other business logic errors
			return ResponseEntity.badRequest().build();
		} catch (DataAccessException e) {
			// Handle database-related exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (Exception e) {
			// Handle any other unexpected exceptions
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	// public ResponseEntity<MessageResponse> postMessage(@RequestBody MessageDTO
	// messageDTO) {
	// try {
	// messageServiceImpl.postMessage(messageDTO.getRentalId(),
	// messageDTO.getUserId(),
	// messageDTO.getMessage());

	// MessageResponse response = new MessageResponse("Message sended!");

	// return ResponseEntity.status(HttpStatus.OK).body(response);

	// } catch (IllegalArgumentException e) {
	// // Handle invalid input or other business logic errors
	// return ResponseEntity.badRequest().build();
	// } catch (Unauthorized e) {
	// // Handle authentication or authorization errors
	// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	// } catch (MappingException e) {
	// // Handle any other exception related to sending the message
	// return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	// }
	// }

}

// public ResponseEntity<Map<String, String>> postMessage(@RequestBody
// Map<String, Object> requestBody) {
// Long rentalId = Long.parseLong(requestBody.get("rental_id").toString());
// Long userId = Long.parseLong(requestBody.get("user_id").toString());
// String message = requestBody.get("message").toString();

// messageServiceImpl.postMessage(rentalId, userId, message);

// Map<String, String> response = new HashMap<>();
// response.put("message", "Message sended!");

// return ResponseEntity.status(HttpStatus.CREATED).body(response);
// }