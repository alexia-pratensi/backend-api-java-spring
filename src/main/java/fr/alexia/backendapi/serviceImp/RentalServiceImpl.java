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

    @Override
    public RentalDTO updateRental(Long rentalId, String name, int surface, int price, String description) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new EntityNotFoundException("Rental not found for this id :: " + rentalId));

        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setCreated_at(rental.getCreated_at());
        rental.setUpdated_at(new Date());

        Rental updatedRental = rentalRepository.save(rental);

        return convertToDTO(updatedRental);
    }

    @Override
    public RentalDTO getRental(Long id) {
        Rental rental = rentalRepository.findById(id).orElse(null);
        return convertToDTO(rental);
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        if (rentals != null) {
            return rentals.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    private RentalDTO convertToDTO(Rental rental) {
        return modelMapper.map(rental, RentalDTO.class);
    }

}
