package fr.alexia.backendapi.DTO;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InternalUserDTO {
    private Long id;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
}

// public static InternalUserDTO fromEntity(InternalUser user, ModelMapper
// modelMapper) {
// return modelMapper.map(user, InternalUserDTO.class);
// }