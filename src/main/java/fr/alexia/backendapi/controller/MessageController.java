package fr.alexia.backendapi.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.MessageDTO;
import fr.alexia.backendapi.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("/api/messages/")
	public ResponseEntity<MessageDTO> postMessage(@RequestBody Map<String, Object> requestBody) {
		Long rentalId = Long.parseLong(requestBody.get("rental_id").toString());
		Long userId = Long.parseLong(requestBody.get("user_id").toString());
		String message = requestBody.get("message").toString();

		MessageDTO createdMessage = messageService.postMessage(rentalId, userId, message);
		return ResponseEntity.ok(createdMessage);
	}
	// public ResponseEntity<MessageDTO> postMessage(
	// @RequestParam("rental_id") Long rentalId,
	// @RequestParam("user_id") Long userId,
	// @RequestParam("message") String message) {
	// MessageDTO createdMessage = messageService.postMessage(rentalId, userId,
	// message);
	// return ResponseEntity.ok(createdMessage);
	// }
	// public ResponseEntity<MessageDTO> postMessage(@RequestBody MessageRequest
	// messageRequest) {
	// MessageDTO createdMessage = messageService.postMessage(
	// messageRequest.getRentalId(),
	// messageRequest.getUserId(),
	// messageRequest.getMessage());
	// return ResponseEntity.ok(createdMessage);
	// }

}
