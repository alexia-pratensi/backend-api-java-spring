package fr.alexia.backendapi.serviceImp;

import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
import fr.alexia.backendapi.DTO.MessageDTO;
import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.MessageRepository;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.MessageService;
import jakarta.persistence.EntityNotFoundException;

@Service
// @Transactional
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

		Rental rental = rentalRepository.findById(rentalId)
				.orElseThrow(() -> new EntityNotFoundException("Rental not found for this id :: " + rentalId));

		InternalUser user = userRepository.findById(userId)
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
