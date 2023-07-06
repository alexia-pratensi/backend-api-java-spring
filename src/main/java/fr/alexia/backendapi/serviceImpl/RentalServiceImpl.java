package fr.alexia.backendapi.serviceImpl;

import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.modelDTO.InternalUserDTO;
import fr.alexia.backendapi.modelDTO.RentalDTO;
import fr.alexia.backendapi.mapper.RentalMapper;
import fr.alexia.backendapi.mapper.UserMapper;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.service.RentalService;
import fr.alexia.backendapi.model.InternalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RentalServiceImpl implements RentalService {
	
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private RentalMapper rentalMapper;
    @Autowired
    private UserServiceImpl userServiceImpl ;


    @Override
    public RentalDTO createRental(String name, int surface, int price, String description, String picture, Long ownerId) {
        Rental rental = new Rental();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setPicture(picture);
        
        InternalUserDTO ownerDTO = userServiceImpl.getUserById(ownerId);
        InternalUser owner = UserMapper.MAPPER.mapToInternalUser(ownerDTO);
        rental.setOwnerId(owner);
        
        Rental savedRental = rentalRepository.save(rental);
        return rentalMapper.mapToRentalDTO(savedRental);
    }

    @Override
    public RentalDTO updateRental(Long rentalId, String name, int surface, int price, String description, String picture) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setDescription(description);
            rental.setPicture(picture);

            Rental updatedRental = rentalRepository.save(rental);
            return rentalMapper.mapToRentalDTO(updatedRental);
        } else {
            throw new RuntimeException("Rental with ID " + rentalId + " not found");
        }
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return rentals.stream()
                .map(rentalMapper::mapToRentalDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDTO getRentalById(Long rentalId) {
        Optional<Rental> optionalRental = rentalRepository.findById(rentalId);
        if (optionalRental.isPresent()) {
            Rental rental = optionalRental.get();
            return rentalMapper.mapToRentalDTO(rental);
        } else {
            throw new RuntimeException("Rental with ID " + rentalId + " not found");
        }
    }
}
