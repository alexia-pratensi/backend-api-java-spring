package fr.alexia.backendapi.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.alexia.backendapi.mapper.MessageMapper;
import fr.alexia.backendapi.mapper.RentalMapper;
import fr.alexia.backendapi.mapper.UserMapper;
import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.modelDTO.InternalUserDTO;
import fr.alexia.backendapi.modelDTO.MessageDTO;
import fr.alexia.backendapi.modelDTO.RentalDTO;
import fr.alexia.backendapi.repository.MessageRepository;
import fr.alexia.backendapi.service.MessageService;


@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@Autowired
	private RentalServiceImpl rentalServiceImpl;
	
	@Autowired
	private MessageMapper messageMapper;

	@Override
	public MessageDTO createMessage(Long rental_id, Long user_id, String message) {
	    InternalUserDTO userDTO = userServiceImpl.getUserById(user_id);
	    RentalDTO rentalDTO = rentalServiceImpl.getRentalById(rental_id);

	    Message messageCreated = new Message();
	    messageCreated.setRentalId(RentalMapper.MAPPER.mapToRental(rentalDTO));
	    messageCreated.setUserId(UserMapper.MAPPER.mapToInternalUser(userDTO));

//	    messageCreated.setRentalId(rental);
//	    messageCreated.setUserId(user);
	    messageCreated.setMessage(message);

	    Message savedMessage = messageRepository.save(messageCreated);
	    return messageMapper.mapToMessageDTO(savedMessage);
	}


}

//package fr.alexia.backendapi.serviceImpl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Service;
//import fr.alexia.backendapi.mapper.MessageMapper;
//import fr.alexia.backendapi.model.InternalUser;
//import fr.alexia.backendapi.model.Message;
//import fr.alexia.backendapi.model.Rental;
//import fr.alexia.backendapi.modelDTO.MessageDTO;
//import fr.alexia.backendapi.repository.IUserRepository;
//import fr.alexia.backendapi.repository.MessageRepository;
//import fr.alexia.backendapi.repository.RentalRepository;
//import fr.alexia.backendapi.service.MessageService;
//
//@Service
//public class MessageServiceImpl implements MessageService {
//	
//	@Autowired
//	private MessageRepository messageRepository;
//	private UserServiceImpl userServiceImpl;
//	private RentalServiceImpl rentalServiceImpl;
//	
//
//
//    @Override
//    public MessageDTO createMessage(Long rental_id, Long user_id, String message) {
//      InternalUser user = userServiceImpl.getUser_id(user_id);
//      Rental rental = rentalServiceImpl.getRentals(rental_id);
//      Message messageCreated = new Message(null, rental, user, message, null, null);
//      
//      messageRepository.save(messageCreated);
//    }
//}
