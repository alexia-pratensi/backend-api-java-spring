package fr.alexia.backendapi.service;

import java.util.List;
import fr.alexia.backendapi.DTO.RentalDTO;

public interface RentalService {

    RentalDTO getRentalById(Long id);

    List<RentalDTO> getAllRentals();

    RentalDTO createRental(RentalDTO rentalDTO);

    RentalDTO updateRental(Long id, RentalDTO rentalDTO);

}
