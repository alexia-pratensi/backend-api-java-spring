package fr.alexia.backendapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<MessageDTO> createMessage(@RequestBody MessageDTO messageDTO) {
        MessageDTO createdMessageDTO = messageService.createMessage(messageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessageDTO);
    }
	
}
