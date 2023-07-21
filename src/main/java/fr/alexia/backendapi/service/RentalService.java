package fr.alexia.backendapi.service;

import java.util.List;
import fr.alexia.backendapi.DTO.RentalDTO;

public interface RentalService {

    RentalDTO getRental(Long id);

    List<RentalDTO> getAllRentals();

    RentalDTO updateRental(Long rentalId, String name, int surface, int price, String picture, String description,
            Long ownerId);

    RentalDTO createRental(String name, int surface, int price, String picture, String description, Long ownerId);

}
