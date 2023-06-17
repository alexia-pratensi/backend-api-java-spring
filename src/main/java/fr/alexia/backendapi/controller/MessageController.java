package fr.alexia.backendapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	/**
	 * Create - Add a new message
	 * @param message An object message
	 * @return The message object saved
	 */
	@PostMapping("/api/messages/")
	public Message createMessage(@RequestBody Message message) {
		return messageService.saveMessage(message);
	}
	

}
