package fr.alexia.backendapi.serviceImp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.DTO.ModelMapperConfig;
import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.repository.UserRepository;
import fr.alexia.backendapi.service.RentalService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalServiceImpl implements RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RentalDTO createRental(String name, int surface, int price, String picture, String description,
            Long ownerId) {
        userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found for this id :: " + ownerId));

        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);
        rental.setOwnerId(ownerId);
        rental.setCreatedAt(new Date());
        rental.setUpdatedAt(new Date());

        Rental savedRental = rentalRepository.save(rental);

        return convertToDTO(savedRental);
    }

    @Override
    public RentalDTO updateRental(Long rentalId, String name, int surface, int price, String picture,
            String description, Long ownerId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found for this id :: " + rentalId));

        InternalUser owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found for this id :: " + ownerId));

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setPicture(picture);
        rental.setDescription(description);
        rental.setOwnerId(ownerId);
        rental.setCreatedAt(rental.getCreatedAt());
        rental.setUpdatedAt(new Date());

        Rental updatedRental = rentalRepository.save(rental);

        return convertToDTO(updatedRental);
    }

    @Override
    public RentalDTO getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id).orElse(null);
        return convertToDTO(rental);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        System.out.println(rentals);
        return rentals.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private RentalDTO convertToDTO(Rental rental) {
        return modelMapper.map(rental, RentalDTO.class);
    }
    // @Override
    // public List<RentalDTO> getAllRentals() {
    // List<Rental> rentals = rentalRepository.findAll();
    // List<RentalDTO> rentalsDTO = new ArrayList<RentalDTO>();
    // for (Rental rental : rentals) {
    // rentalsDTO.add(convertToDTO(rental));
    // }
    // return rentalsDTO;
    // }

}
