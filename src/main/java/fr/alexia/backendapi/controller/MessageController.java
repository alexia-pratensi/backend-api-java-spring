package fr.alexia.backendapi.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.service.MessageService;

@RestController
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("/api/messages/")
	public ResponseEntity<Map<String, String>> postMessage(@RequestBody Map<String, Object> requestBody) {
		Long rentalId = Long.parseLong(requestBody.get("rental_id").toString());
		Long userId = Long.parseLong(requestBody.get("user_id").toString());
		String message = requestBody.get("message").toString();

		messageService.postMessage(rentalId, userId, message);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Rental updated!");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
