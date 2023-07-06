package fr.alexia.backendapi.service;

import fr.alexia.backendapi.modelDTO.MessageDTO;

public interface MessageService {
    MessageDTO createMessage(Long rental_id, Long user_id, String message);
}






//package fr.alexia.backendapi.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import fr.alexia.backendapi.model.InternalUser;
//import fr.alexia.backendapi.model.Message;
//import fr.alexia.backendapi.model.Rental;
//import fr.alexia.backendapi.modelDTO.MessageDTO;
//import fr.alexia.backendapi.repository.IUserRepository;
//import fr.alexia.backendapi.repository.MessageRepository;
//import fr.alexia.backendapi.repository.RentalRepository;
//
//@Service
//public class MessageService {
//	
//	@Autowired
//	private MessageRepository messageRepository;
//	
//	@Autowired
//	private RentalRepository rentalRepository;
//
//	@Autowired
//	private IUserRepository userRepository;
//
//	public Message saveMessage(MessageDTO message) {
//	    // Check if the message or rental ID is null
//	    if (message == null || message.getRental_id() == null) {
//	        throw new IllegalArgumentException("Message object or rental ID is null");
//	    }
//
//	    // Retrieve the rental associated with the rental ID
//	    Rental rental = rentalRepository.findById(message.getRental_id())
//	            .orElseThrow(() -> new RuntimeException("Rental with the specified ID not found"));
//
//	    // Retrieve the user associated with the user ID
//	    InternalUser user = userRepository.findById(message.getUser_id())
//	            .orElseThrow(() -> new RuntimeException("InternalUser not found"));
//
//	    // Set the rental and user for the message
//	    Message entity = new Message();
//	    
//	    entity.setMessage(message.getMessage());
//	    entity.setRental(rental);
//	    entity.setUser(user);
//	    entity.setCreated_at(message.getCreated_at());
//	    entity.setUpdated_at(message.getUpdated_at());
//
//	    // Save the message
//	    Message savedMessage = messageRepository.save(entity);
//
//	    // Perform additional operations if necessary with the saved message
//
//	    return savedMessage; // Return the saved message
//	}
//
//}
////	public Message saveMessage(Message message) {
////	Message savedMessage= messageRepository.save(message);
////	return savedMessage;
////	}