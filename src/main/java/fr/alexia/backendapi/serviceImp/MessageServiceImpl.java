package fr.alexia.backendapi.serviceImp;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.alexia.backendapi.DTO.MessageDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.MessageRepository;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.MessageService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
    private MessageRepository messageRepository;
	@Autowired
    private ModelMapper modelMapper;
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private RentalRepository rentalRepository;
    
//    @Override
//    public MessageDTO createMessage(MessageDTO messageDTO) {
//        Message message = modelMapper.map(messageDTO, Message.class);
//        message.setCreatedAt(LocalDateTime.now());
//        message.setUpdatedAt(LocalDateTime.now());
//        Message createdMessage = messageRepository.save(message);
//        return modelMapper.map(createdMessage, MessageDTO.class);
//    }
	@Override
	public MessageDTO createMessage(MessageDTO messageDTO) {
	    Message message = modelMapper.map(messageDTO, Message.class);
	    message.setCreatedAt(LocalDateTime.now());
	    message.setUpdatedAt(LocalDateTime.now());

	    // Set the rental by ID
	    Long rentalId = messageDTO.getRental_id().getId();
	    Rental rental = rentalRepository.findById(rentalId)
	            .orElseThrow(() -> new EntityNotFoundException("Rental with ID " + rentalId + " not found"));
	    message.setRental(rental);

	    // Set the user by ID
	    Long userId = messageDTO.getUser_id().getId();
	    InternalUser user = userRepository.findById(userId)
	            .orElseThrow(() -> new EntityNotFoundException("User with ID " + userId + " not found"));
	    message.setUser(user);

	    Message createdMessage = messageRepository.save(message);
	    return modelMapper.map(createdMessage, MessageDTO.class);
	}



}
