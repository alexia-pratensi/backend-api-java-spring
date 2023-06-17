package fr.alexia.backendapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.alexia.backendapi.model.Rental;
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
	public Iterable<Rental> getRentals() {
		return rentalService.getRentals();
	}
	
	/**
	 * Read - Get one rental 
	 * @param id The id of the rental
	 * @return a rental object full filled
	 */
	@GetMapping("/api/rentals/{id}")
	public Rental getRental(@PathVariable("id") final Long id) {
		Optional<Rental> rental = rentalService.getRental(id);
		if(rental.isPresent()) {
			return rental.get();
		} else {
			return null;
		}
	}
	
	/**
	 * Create - Add a new rental
	 * @param rental An object rental
	 * @return The rental object saved
	 */
	@PostMapping("/api/rentals/{id}")
	public Rental createRental(@RequestBody Rental rental) {
		return rentalService.saveRental(rental);
	}
	
	/**
	 * Update - Update an existing rental
	 * @param id - The id of the rental to update
	 * @param rental - The rental object updated
	 * @return
	 */
	@PutMapping("/api/rentals/{id}")
	public Rental updateRental(@PathVariable("id") final Long id, @RequestBody Rental rental) {
		Optional<Rental> e = rentalService.getRental(id);
		if(e.isPresent()) {
			Rental currentRental = e.get();
			
			String name = rental.getName();
			if(name != null) {
				currentRental.setName(name);
			}
			int surface = rental.getSurface();
			if(surface != 0) {
				currentRental.setSurface(surface);
			}
			int price = rental.getPrice();
			if(price != 0) {
				currentRental.setPrice(price);
			}
			String picture = rental.getPicture();
			if(picture != null) {
				currentRental.setPicture(picture);
			}
			String description = rental.getDescription();
			if(description != null) {
				currentRental.setDescription(description);
			}
			int ownerId = rental.getOwnerId();
			if(ownerId != 0) {
				currentRental.setOwnerId(ownerId);
			}
			rentalService.saveRental(currentRental);
			return currentRental;
		} else {
			return null;
		}
	}
	
}
