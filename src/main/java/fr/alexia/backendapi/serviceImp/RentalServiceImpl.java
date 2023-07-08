package fr.alexia.backendapi.serviceImp;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.alexia.backendapi.DTO.RentalDTO;
import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.DTO.InternalUserDTO;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.repository.RentalRepository;
import fr.alexia.backendapi.service.RentalService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class RentalServiceImpl implements RentalService {
	
	@Autowired
    private RentalRepository rentalRepository;
	
	@Autowired
    private RentalRepository userRepository;
	
	@Autowired
    private  ModelMapper modelMapper;
    
    @Override
    public RentalDTO getRentalById(Long id) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() ->new NoSuchElementException("Rental not found with id: " + id));
        return modelMapper.map(rental, RentalDTO.class);
    }

//    @Override
//    public List<RentalDTO> getAllRentals() {
//        List<Rental> rentals = rentalRepository.findAll();
//        return rentals.stream()
//                .map(rental -> modelMapper.map(rental, RentalDTO.class))
//                .collect(Collectors.toList());
//    }

    @Override
    public List<RentalDTO> getAllRentals() {
        List<Rental> rentals = rentalRepository.findAll();
        return convertToRentalDTOList(rentals);
    }

    private List<RentalDTO> convertToRentalDTOList(List<Rental> rentals) {
        return rentals.stream()
                .map(rental -> {
                    RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
                    Long ownerId = rental.getOwner().getId();
                    InternalUserDTO ownerDTO = new InternalUserDTO();
                    ownerDTO.setId(ownerId);
                    rentalDTO.setOwner(ownerDTO);
                    // Ajoutez d'autres propriétés si nécessaire
                    return rentalDTO;
                })
                .collect(Collectors.toList());
    }




    @Override
    public RentalDTO createRental(RentalDTO rentalDTO) {
    	 Rental rental = modelMapper.map(rentalDTO, Rental.class);
         rental.setCreatedAt(LocalDateTime.now());
         rental.setUpdatedAt(LocalDateTime.now());
         Rental createdRental = rentalRepository.save(rental);
         return modelMapper.map(createdRental, RentalDTO.class);
//        Rental rental = modelMapper.map(rentalDTO, Rental.class);
//        Rental savedRental = rentalRepository.save(rental);
//        return modelMapper.map(savedRental, RentalDTO.class);
    }

    @Override
    public RentalDTO updateRental(Long id, RentalDTO rentalDTO) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rental not found with id: " + id));
        
        rental.setName(rentalDTO.getName());
        rental.setSurface(rentalDTO.getSurface());
        rental.setPrice(rentalDTO.getPrice());
        rental.setPicture(rentalDTO.getPicture());
        rental.setDescription(rentalDTO.getDescription());
        rental.setUpdatedAt(LocalDateTime.now());
     
        Rental updatedRental = rentalRepository.save(rental);
        return modelMapper.map(updatedRental, RentalDTO.class);
    }

}

