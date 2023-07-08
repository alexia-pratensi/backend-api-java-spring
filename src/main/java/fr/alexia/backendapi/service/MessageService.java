package fr.alexia.backendapi.service;

import fr.alexia.backendapi.DTO.MessageDTO;

public interface MessageService {
	
    MessageDTO createMessage(MessageDTO messageDTO);
   
}