package fr.alexia.backendapi.mapper;

import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import fr.alexia.backendapi.model.Rental;

import fr.alexia.backendapi.modelDTO.RentalDTO;

@Mapper
public interface RentalMapper {
	
	RentalMapper MAPPER = Mappers.getMapper(RentalMapper.class);
	
	@Mapping(target = "owner_id", source = "ownerId.id")
	RentalDTO mapToRentalDTO(Rental rental);
	
	@Mapping(target = "ownerId.id", source = "owner_id")
	Rental mapToRental(RentalDTO rentalDTO);

	default Date mapCreatedAt(Rental rental) {
		return rental.getCreated_at();
	}

	default void mapCreatedAt(Date createdAt, @MappingTarget Rental rental) {
		rental.setCreated_at(createdAt);
	}
	
	// New method for mapping from Rental to RentalDTO
		RentalDTO mapRentalToRentalDTO(Rental rental);
	

}
