package fr.alexia.backendapi.DTO;
//
//import java.time.LocalDate;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class RentalDTO {
//	  private Long id;
//	    private String name;
//	    private int surface;
//	    private int price;
//	    private String picture;
//	    private String description;
//	    @JsonProperty("owner_id")
//	    private Long owner;
//	    private LocalDate createdAt;
//	    private LocalDate updatedAt;
//}
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDTO {
	  private Long id;
	    private String name;
	    private int surface;
	    private int price;
	    private String picture;
	    private String description;
	    @JsonProperty("owner_id")
	    private InternalUserDTO owner;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
}