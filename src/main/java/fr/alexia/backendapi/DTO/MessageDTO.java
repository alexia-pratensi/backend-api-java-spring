package fr.alexia.backendapi.DTO;

import java.util.Date;
import fr.alexia.backendapi.model.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MessageDTO {
    private Long id;
    private Long rentalId;
    private Long userId;
    private String message;
    private Date createdAt;
    private Date updatedAt;

}

// public static MessageDTO fromEntity(Message message) {
// MessageDTO messageDTO = new MessageDTO();
// messageDTO.setId(message.getId());
// messageDTO.setRentalId(message.getRental());
// messageDTO.setUserId(message.getUser());
// messageDTO.setMessage(message.getMessage());
// messageDTO.setCreatedAt(message.getCreatedAt());
// messageDTO.setUpdatedAt(message.getUpdatedAt());
// return messageDTO;
// }