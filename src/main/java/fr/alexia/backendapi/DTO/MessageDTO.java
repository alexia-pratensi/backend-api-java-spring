//package fr.alexia.backendapi.DTO;
//
//import java.time.LocalDate;
//import java.util.Date;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//public class MessageDTO {
//	private Long id;
//	@JsonProperty("rental_id")
//    private Long rental;
//	@JsonProperty("user_id")
//    private Long user;
//    private String message;
//    private LocalDate createdAt;
//    private LocalDate updatedAt;
//}
package fr.alexia.backendapi.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MessageDTO {
	private Long id;
	@JsonProperty("rental_id")
    private RentalDTO rental_id;
	@JsonProperty("user_id")
    private InternalUserDTO user_id;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}