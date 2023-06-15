package fr.alexia.backendapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.repository.MessageRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;

	public Message saveMessage(Message message) {
        Message savedMessage= messageRepository.save(message);
        return savedMessage;
    }

}
