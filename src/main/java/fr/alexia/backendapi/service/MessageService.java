package fr.alexia.backendapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.IUserRepository;
import fr.alexia.backendapi.repository.MessageRepository;
import fr.alexia.backendapi.repository.RentalRepository;

@Service
public class MessageService {
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private IUserRepository userRepository;

	public Message saveMessage(Message message) {
	    // Check if the message or rental ID is null
	    if (message == null || message.getRental() == null) {
	        throw new IllegalArgumentException("Message object or rental ID is null");
	    }

	    // Retrieve the rental associated with the rental ID
	    Rental rental = rentalRepository.findById(message.getRental().getId())
	            .orElseThrow(() -> new RuntimeException("Rental with the specified ID not found"));

	    // Retrieve the user associated with the user ID
	    InternalUser user = userRepository.findById(message.getUser().getId())
	            .orElseThrow(() -> new RuntimeException("InternalUser not found"));

	    // Set the rental and user for the message
	    message.setRental(rental);
	    message.setUser(user);

	    // Save the message
	    Message savedMessage = messageRepository.save(message);

	    // Perform additional operations if necessary with the saved message

	    return savedMessage; // Return the saved message
	}

}
//	public Message saveMessage(Message message) {
//	Message savedMessage= messageRepository.save(message);
//	return savedMessage;
//	}