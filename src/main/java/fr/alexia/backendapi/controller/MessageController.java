package fr.alexia.backendapi.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.MessageRequest;
import fr.alexia.backendapi.DTO.ResponseRequest;
import fr.alexia.backendapi.serviceImp.MessageServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class MessageController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageServiceImpl messageServiceImpl;

	@PostMapping("/messages")
	public ResponseEntity<ResponseRequest> postMessage(@RequestBody MessageRequest messageDTO) {
		try {
			logger.info("messageDTO is: ", messageDTO.getRental_id());

			messageServiceImpl.postMessage(messageDTO.getRental_id(), messageDTO.getUser_id(), messageDTO.getMessage());

			ResponseRequest response = new ResponseRequest("Message sent!");
			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {
			// Handle invalid input or other business logic errors
			logger.error("exception is: ", e);
			return ResponseEntity.badRequest().build();

		} catch (DataAccessException e) {
			// Handle database-related exceptions
			logger.error("exception is: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		} catch (Exception e) {
			// Handle any other unexpected exceptions
			logger.error("exception is: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
