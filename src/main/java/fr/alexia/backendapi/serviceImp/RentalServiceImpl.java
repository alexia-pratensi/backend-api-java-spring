package fr.alexia.backendapi.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.exceptions.ApiException;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.service.RentalService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Create a new rental with the provided details.
     * 
     * @param name:        The name of the rental.
     * @param surface:     The surface area of the rental.
     * @param price:       The price of the rental.
     * @param picture:     The URL of the rental's picture.
     * @param description: The description of the rental.
     * @param owner_id:    The ID of the owner associated with the rental.
     * @return RentalDTO: The created rental as a Data Transfer Object (DTO).
     */
    @Override
    public RentalDTO createRental(String name, int surface, int price, String picture, String description,
            Long owner_id) {

        InternalUserDTO owner = userServiceImpl.getCurrentUser();

        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);
        rental.setOwner_id(owner.getId());
        rental.setCreated_at(new Date());
        rental.setUpdated_at(new Date());

        Rental savedRental = rentalRepository.save(rental);

        return convertToDTO(savedRental);
    }

    /**
     * Update an existing rental with the provided details.
     * 
     * @param id:          The ID of the rental to update.
     * @param name:        The updated name of the rental.
     * @param surface:     The updated surface area of the rental.
     * @param price:       The updated price of the rental.
     * @param description: The updated description of the rental.
     * @return RentalDTO: The updated rental as a Data Transfer Object (DTO).
     * @throws EntityNotFoundException: If the rental with the specified ID is not
     *                                  found.
     */
    @Override
    public RentalDTO updateRental(Long id, String name, int surface, int price, String description) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found for this id :: " + id));

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setCreated_at(rental.getCreated_at());
        rental.setUpdated_at(new Date());

        Rental updatedRental = rentalRepository.save(rental);

        return convertToDTO(updatedRental);
    }

    /**
     * Get a rental by its ID.
     * 
     * @param id: The ID of the rental to retrieve.
     * @return RentalDTO: The rental as a Data Transfer Object (DTO).
     * @throws ApiException.NotFoundException: If the rental with the specified ID
     *                                         is not found.
     */
    @Override
    public RentalDTO getRental(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new ApiException.NotFoundException("Rental not found with ID: " + id));
        return convertToDTO(rental);
    }

    /**
     * Get all rentals from the database.
     * 
     * @return List<RentalDTO>: A list of rentals as Data Transfer Objects (DTOs).
     */
    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        // If rentals list is not empty, convert each rental to RentalDTO and return the
        // list
        if (rentals != null) {
            return rentals.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        // If rentals list is empty, return an empty list of RentalDTOs
        return new ArrayList<>();
    }

    /**
     * Convert a Rental entity to a RentalDTO.
     * 
     * @param rental: The Rental entity to convert.
     * @return RentalDTO: The Rental entity as a Data Transfer Object (DTO).
     */
    private RentalDTO convertToDTO(Rental rental) {
        return modelMapper.map(rental, RentalDTO.class);
    }

}
