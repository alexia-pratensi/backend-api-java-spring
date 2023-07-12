package fr.alexia.backendapi.service;

import java.util.List;
import fr.alexia.backendapi.DTO.RentalDTO;

public interface RentalService {

    RentalDTO getRentalById(Long id);

    List<RentalDTO> getAllRentals();

    RentalDTO updateRental(Long id, RentalDTO rentalDTO);

    RentalDTO createRental(String name, int surface, int price, String picture, String description, Long ownerId);

}
