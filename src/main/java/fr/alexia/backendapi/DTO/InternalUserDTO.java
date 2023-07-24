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
    private String password;
    private Date created_at;
    private Date updated_at;
}