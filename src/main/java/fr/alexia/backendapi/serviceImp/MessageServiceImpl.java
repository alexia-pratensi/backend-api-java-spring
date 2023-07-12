package fr.alexia.backendapi.serviceImp;

import java.util.Date;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.alexia.backendapi.DTO.MessageDTO;
import fr.alexia.backendapi.model.Message;
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
	private RentalRepository rentalRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public MessageDTO postMessage(Long rentalId, Long userId, String message) {

		rentalRepository.findById(rentalId)
				.orElseThrow(() -> new EntityNotFoundException("Rental not found for this id :: " + rentalId));

		userRepository.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("User not found for this id :: " + userId));

		Message newMessage = new Message();
		newMessage.setRentalId(rentalId);
		newMessage.setUserId(userId);
		newMessage.setMessage(message);
		newMessage.setCreatedAt(new Date());
		newMessage.setUpdatedAt(new Date());

		Message savedMessage = messageRepository.save(newMessage);

		return convertToDTO(savedMessage);
	}

	private MessageDTO convertToDTO(Message message) {
		return modelMapper.map(message, MessageDTO.class);
	}

}
