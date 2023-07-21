package fr.alexia.backendapi.DTO;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
	private Long ownerId;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date created_at;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date updated_at;

}
