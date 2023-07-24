package fr.alexia.backendapi.controller;

import java.io.IOException;
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
import fr.alexia.backendapi.DTO.RentalsResponse;
import fr.alexia.backendapi.DTO.ResponseRequest;
import fr.alexia.backendapi.service.FileUploadService;
import fr.alexia.backendapi.serviceImp.RentalServiceImpl;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/api")
public class RentalController {

	@Autowired
	private RentalServiceImpl rentalServiceImpl;

	@Autowired
	private FileUploadService fileUpload;

	@GetMapping("/rentals")
	public ResponseEntity<RentalsResponse> getAllRentals() {
		List<RentalDTO> rentalDTOs = rentalServiceImpl.getAllRentals();
		RentalsResponse response = new RentalsResponse(rentalDTOs);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/rentals/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		RentalDTO rentalDTO = rentalServiceImpl.getRental(id);
		if (rentalDTO != null) {
			return ResponseEntity.ok(rentalDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/rentals")
	public ResponseEntity<ResponseRequest> createRental(@RequestParam("name") String name,
			@RequestParam("surface") int surface,
			@RequestParam("price") int price,
			@RequestParam("picture") MultipartFile picture,
			@RequestParam("description") String description,
			Long owner_id) throws IOException {

		String pictureUrl = fileUpload.uploadFile(picture);
		rentalServiceImpl.createRental(name, surface, price, pictureUrl, description, owner_id);

		ResponseRequest response = new ResponseRequest("Rental created!");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/rentals/{rentalId}")
	public ResponseEntity<ResponseRequest> updateRental(@PathVariable Long rentalId,
			@RequestParam("name") String name,
			@RequestParam("surface") int surface,
			@RequestParam("price") int price,
			@RequestParam("description") String description) throws IOException {

		rentalServiceImpl.updateRental(rentalId, name, surface, price, description);

		ResponseRequest response = new ResponseRequest("Rental updated!");

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
