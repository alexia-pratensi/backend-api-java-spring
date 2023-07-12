package fr.alexia.backendapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.service.RentalService;

@RestController
public class RentalController {

	@Autowired
	private RentalService rentalService;

	/**
	 * Read - Get all rentals
	 * 
	 * @return - An Iterable object of rental full filled
	 */
	@GetMapping("/api/rentals")
	public ResponseEntity<List<RentalDTO>> getAllRentals() {
		List<RentalDTO> rentalDTOs = rentalService.getAllRentals();
		return ResponseEntity.ok(rentalDTOs);
	}

	/**
	 * Read - Get one rental
	 * 
	 * @param id The id of the rental
	 * @return a rental object full filled
	 */
	@GetMapping("/api/rentals/{id}")
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
		RentalDTO rentalDTO = rentalService.getRentalById(id);
		if (rentalDTO != null) {
			return ResponseEntity.ok(rentalDTO);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	/**
	 * Create - Add a new rental
	 * 
	 * @param rental An object rental
	 * @return The rental object saved
	 */
	@PostMapping("/api/rentals/")
	public ResponseEntity<Map<String, String>> createRental(@RequestBody Map<String, Object> requestBody) {
		String name = requestBody.get("name").toString();
		int surface = Integer.parseInt(requestBody.get("surface").toString());
		int price = Integer.parseInt(requestBody.get("price").toString());
		String picture = requestBody.get("picture").toString();
		String description = requestBody.get("description").toString();
		Long ownerId = Long.parseLong(requestBody.get("owner_id").toString());

		rentalService.createRental(name, surface, price, picture, description, ownerId);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Rental created!");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	/**
	 * Update - Update an existing rental
	 * 
	 * @param id     - The id of the rental to update
	 * @param rental - The rental object updated
	 * @return
	 */

	@PutMapping("/api/rentals/{rentalId}")
	public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long rentalId,
			@RequestBody Map<String, Object> requestBody) {
		String name = requestBody.get("name").toString();
		int surface = Integer.parseInt(requestBody.get("surface").toString());
		int price = Integer.parseInt(requestBody.get("price").toString());
		String picture = requestBody.get("picture").toString();
		String description = requestBody.get("description").toString();
		Long ownerId = Long.parseLong(requestBody.get("owner_id").toString());

		rentalService.updateRental(rentalId, name, surface, price, picture, description, ownerId);

		Map<String, String> response = new HashMap<>();
		response.put("message", "Rental updated!");

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
