package fr.alexia.backendapi.mapper;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.modelDTO.InternalUserDTO;

@Mapper
public interface UserMapper {

	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);
	
	InternalUserDTO mapToInternalUserDTO(InternalUser user);
	
	InternalUser mapToInternalUser(InternalUserDTO userDTO);
	
	default Date mapCreatedAt(Rental rental) {
		return rental.getCreated_at();
	}

	default void mapCreatedAt(Date createdAt, @MappingTarget Rental rental) {
		rental.setCreated_at(createdAt);
	}
	
}
