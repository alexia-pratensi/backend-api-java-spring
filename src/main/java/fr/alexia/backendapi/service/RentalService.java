package fr.alexia.backendapi.service;

import fr.alexia.backendapi.modelDTO.RentalDTO;
import java.util.List;

public interface RentalService {

    RentalDTO createRental(String name, int surface, int price, String description, String picture, Long ownerId);

    RentalDTO updateRental(Long rentalId, String name, int surface, int price, String description, String picture);

    List<RentalDTO> getAllRentals();

    RentalDTO getRentalById(Long rentalId);
}


//package fr.alexia.backendapi.service;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import fr.alexia.backendapi.model.Rental;
//import fr.alexia.backendapi.repository.RentalRepository;
//
//@Service
//public class RentalService {
// @Autowired
//    private RentalRepository rentalRepository;
//
//    public Optional<Rental> getRental(Long id) {
//        return rentalRepository.findById(id);
//    }
//
//    public Iterable<Rental> getRentals() {
//        return rentalRepository.findAll();
//    }
//
//    public Rental saveRental(Rental rental) {
//        Rental savedRental = rentalRepository.save(rental);
//        return savedRental;
//    }
//
//}
