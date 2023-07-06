package fr.alexia.backendapi.mapper;

import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import fr.alexia.backendapi.model.InternalUser;
import fr.alexia.backendapi.model.Message;
import fr.alexia.backendapi.model.Rental;
import fr.alexia.backendapi.modelDTO.MessageDTO;


@Mapper
public interface MessageMapper {

    MessageMapper MAPPER = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "rentalId.id", target = "rental_id")
    @Mapping(source = "userId.id", target = "user_id")
    MessageDTO mapToMessageDTO(Message message);

    @Mapping(source = "rental_id", target = "rentalId.id")
    @Mapping(source = "user_id", target = "userId.id")
    Message mapToMessage(MessageDTO messageDTO);

    default void mapCreatedAt(Date createdAt, @MappingTarget Message message) {
        message.setCreated_at(createdAt);
    }

    default void mapUpdatedAt(Date updatedAt, @MappingTarget Message message) {
        message.setUpdated_at(updatedAt);
    }
    default Long mapRentalToRentalId(Rental rental) {
        return rental.getId();
    }

    default Long mapInternalUserToUserId(InternalUser user) {
        return user.getId();
    }
}
