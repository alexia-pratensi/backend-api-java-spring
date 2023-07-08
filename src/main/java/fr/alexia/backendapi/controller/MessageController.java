package fr.alexia.backendapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.alexia.backendapi.modelDTO.MessageDTO;
import fr.alexia.backendapi.service.MessageService;

@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messages")
    public ResponseEntity<MessageDTO> createMessage(@RequestBody Long rental_id, Long user_id, String message) {
        MessageDTO createdMessage = messageService.createMessage(rental_id,user_id,message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
//        "message": "Message send with success" = ResponseEntity.ok("Message sent with success"); ??
    } 
}


//package fr.alexia.backendapi.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import fr.alexia.backendapi.model.Message;
//import fr.alexia.backendapi.modelDTO.MessageDTO;
//import fr.alexia.backendapi.service.MessageService;
//
//@RestController
//public class MessageController {
//	
//	@Autowired
//	private MessageService messageService;
//	
//	/**
//	 * Create - Add a new message
//	 * @param message An object message
//	 * @return The message object saved
//	 */
//	@PostMapping("/api/messages/")
//	public Message createMessage(@RequestBody MessageDTO message) {
//		return messageService.saveMessage(message);
//	}
//	
//
//}
