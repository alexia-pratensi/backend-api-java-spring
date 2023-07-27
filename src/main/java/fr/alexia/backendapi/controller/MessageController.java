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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class MessageController {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(MessageController.class);

	@Autowired
	private MessageServiceImpl messageServiceImpl;

	/**
	 * Post a new message
	 *
	 * @param messageDTO The MessageRequest object containing rental_id, user_id,
	 *                   and message.
	 * @return ResponseEntity<ResponseRequest> A response entity containing the
	 *         result of the message posting operation.
	 */
	@Operation(summary = "Post a new message", description = "Route for posting a new message.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Message sent", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseRequest.class))
			}),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
	})
	@PostMapping("/messages")
	public ResponseEntity<ResponseRequest> postMessage(
			@Parameter(description = "rental_id, user_id, message") @Valid @RequestBody MessageRequest messageDTO) {
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
