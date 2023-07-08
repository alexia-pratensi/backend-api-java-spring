//package fr.alexia.backendapi.DTO;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import lombok.Data;
//
//
//@Data
//public class InternalUserDTO {
//	private Long id;
//    private String email;
//    private String name;
//    private String password;
//    private LocalDate createdAt;
//    private LocalDate updatedAt;
//}

package fr.alexia.backendapi.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InternalUserDTO {
	private Long id;
    private String email;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
