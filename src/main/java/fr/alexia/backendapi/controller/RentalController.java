package fr.alexia.backendapi.controller;

import java.util.List;
//import java.util.Optional;

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
//import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.service.RentalService;

@RestController
public class RentalController {

	@Autowired
	private RentalService rentalService;
	
	/**
	 * Read - Get all rentals
	 * @return - An Iterable object of rental full filled
	 */
	@GetMapping("/api/rentals")
	 public ResponseEntity<List<RentalDTO>> getAllRentals() {
        List<RentalDTO> rentalDTOs = rentalService.getAllRentals();
        return ResponseEntity.ok(rentalDTOs);
    }
	
	/**
	 * Read - Get one rental 
	 * @param id The id of the rental
	 * @return a rental object full filled
	 */
	@GetMapping("/api/rentals/{id}")
	 public ResponseEntity<RentalDTO> getRentalById(@PathVariable Long id) {
        RentalDTO rentalDTO = rentalService.getRentalById(id);
        return ResponseEntity.ok(rentalDTO);
//        if (rentalDTO != null) {
//            return ResponseEntity.ok(rentalDTO);
//        }
//        return ResponseEntity.notFound().build();
    }
	
	/**
	 * Create - Add a new rental
	 * @param rental An object rental
	 * @return The rental object saved
	 */
	@PostMapping("/api/rentals/{id}")
	 public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        RentalDTO createdRentalDTO = rentalService.createRental(rentalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRentalDTO);
    }
	
	/**
	 * Update - Update an existing rental
	 * @param id - The id of the rental to update
	 * @param rental - The rental object updated
	 * @return
	 */
	@PutMapping("/api/rentals/{id}")
	public ResponseEntity<RentalDTO> updateRental(@PathVariable Long id, @RequestBody RentalDTO rentalDTO) {
        RentalDTO updatedRentalDTO = rentalService.updateRental(id, rentalDTO);
        return ResponseEntity.ok(updatedRentalDTO);
//        if (updatedRentalDTO != null) {
//            return ResponseEntity.ok(updatedRentalDTO);
//        }
//        return ResponseEntity.notFound().build();
    }
	
}
