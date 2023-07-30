package fr.alexia.backendapi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.DTO.RentalsDTO;
import fr.alexia.backendapi.DTO.ResponseRequest;
import fr.alexia.backendapi.exceptions.ApiException;
import fr.alexia.backendapi.exceptions.ErrorResponse;
import fr.alexia.backendapi.exceptions.ApiException.NotFoundException;
import fr.alexia.backendapi.service.FileUploadService;
import fr.alexia.backendapi.serviceImp.RentalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class RentalController {

	private static final Logger logger = LoggerFactory.getLogger(RentalController.class);

	@Autowired
	private RentalServiceImpl rentalServiceImpl;

	@Autowired
	private FileUploadService fileUpload;

	/**
	 * Get all rentals
	 *
	 * @return ResponseEntity<RentalsDTO> A response entity containing a list of
	 *         rental DTOs.
	 */
	@Operation(summary = "Get all rentals", description = "Route for get all rentals informations. The response is a DTO with a list of DTO containing rentals informations.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found rentals", content = {
					@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = RentalDTO.class)))
			}),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
			@ApiResponse(responseCode = "404", description = "Did not find any rentals", content = @Content)
	})
	@GetMapping("/rentals")
	public ResponseEntity<RentalsDTO> getAllRentals() {
		List<RentalDTO> rentalDTOs = rentalServiceImpl.getAllRentals();
		RentalsDTO response = new RentalsDTO(rentalDTOs);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	/**
	 * Get data from rental requested by ID
	 *
	 * @param id The ID of the rental to be searched.
	 * @return ResponseEntity<?> A response entity containing the rental DTO if
	 *         found, or an error response if not found or invalid ID.
	 */
	@Operation(summary = "Get data from rental requested", description = "Route for get data from rental with id requested. The response is a DTO with data from the rental requested.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Found the rental", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class)) }),
			@ApiResponse(responseCode = "400", description = "Invalid id supplied", content = @Content),
			@ApiResponse(responseCode = "404", description = "rental not found", content = @Content) })
	@GetMapping("/rentals/{id}")
	public ResponseEntity<?> getRentalById(
			@Parameter(description = "id of rental to be searched") @Valid @PathVariable Long id) {
		try {
			if (id == null || id <= 0) {
				throw new ApiException.BadRequestException("Invalid ID supplied: " + id);
			}
			RentalDTO rentalDTO = rentalServiceImpl.getRental(id);
			return ResponseEntity.ok(rentalDTO);

		} catch (ApiException.NotFoundException ex) {
			logger.error("Rental not found with ID: " + id, ex);
			ErrorResponse errorResponse = new ErrorResponse("Rental not found",
					Arrays.asList("Rental with ID " + id + " not found."));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

		} catch (ApiException.BadRequestException ex) {
			logger.error("Invalid ID supplied: " + id, ex);
			ErrorResponse errorResponse = new ErrorResponse("Bad request", Arrays.asList("Invalid ID supplied: " + id));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
	}

	/**
	 * Post a new rental
	 *
	 * @param name        The name of the rental to be created.
	 * @param surface     The surface area of the rental to be created.
	 * @param price       The price of the rental to be created.
	 * @param picture     The picture of the rental to be created.
	 * @param description The description of the rental to be created.
	 * @param owner_id    The ID of the owner of the rental to be created.
	 * @return ResponseEntity<?> A response entity containing the result of the
	 *         rental creation operation.
	 * @throws IOException If an error occurs while uploading the picture.
	 */
	@Operation(summary = "Post a new rental", description = "Route for creating a new rental.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Rental created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PostMapping("/rentals")
	public ResponseEntity<?> createRental(
			@Parameter(description = "name, surface, price, picture, description, owner_id of rental to be created") @Valid @RequestParam("name") String name,
			@Valid @RequestParam("surface") int surface,
			@Valid @RequestParam("price") int price,
			@Valid @RequestParam("picture") MultipartFile picture,
			@Valid @RequestParam("description") String description,
			@Valid Long owner_id) throws IOException {

		try {
			String pictureUrl = fileUpload.uploadFile(picture);
			rentalServiceImpl.createRental(name, surface, price, pictureUrl, description, owner_id);

			ResponseRequest response = new ResponseRequest("Rental created!");
			return ResponseEntity.status(HttpStatus.CREATED).body(response);

		} catch (NotFoundException ex) {
			logger.error("Owner not found with ID: " + owner_id, ex);
			ErrorResponse errorResponse = new ErrorResponse("Owner not found",
					Arrays.asList("Owner with ID " + owner_id + " not found."));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

		} catch (IOException ex) {
			logger.error("An error occurred while uploading the picture.", ex);
			ErrorResponse errorResponse = new ErrorResponse("File upload error",
					Arrays.asList("An error occurred while uploading the picture."));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * Update a rental
	 *
	 * @param id          the ID of the rental to be updated.
	 * @param name        The name of the rental to be updated.
	 * @param surface     The surface area of the rental to be updated.
	 * @param price       The price of the rental to be updated.
	 * @param description The description of the rental to be updated.
	 * @return ResponseEntity<?> A response entity containing the result of the
	 *         rental update operation.
	 * @throws IOException If an error occurs while updating the rental picture.
	 */
	@Operation(summary = "Update a rental", description = "Route for updating an existing rental.")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rental updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ResponseRequest.class))),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
			@ApiResponse(responseCode = "404", description = "Rental not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
	})
	@PutMapping("/rentals/{id}")
	public ResponseEntity<?> updateRental(
			@Parameter(description = "id, name, surface, price, description of rental to be updated") @Valid @PathVariable Long id,
			@Valid @RequestParam("name") String name,
			@Valid @RequestParam("surface") int surface,
			@Valid @RequestParam("price") int price,
			@Valid @RequestParam("description") String description) throws IOException {

		try {
			rentalServiceImpl.updateRental(id, name, surface, price, description);

			ResponseRequest response = new ResponseRequest("Rental updated!");
			return ResponseEntity.ok(response);

		} catch (NotFoundException ex) {
			logger.error("Rental not found with ID: " + id, ex);
			ErrorResponse errorResponse = new ErrorResponse("Rental not found",
					Arrays.asList("Rental with ID " + id + " not found."));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

		}
	}
}
