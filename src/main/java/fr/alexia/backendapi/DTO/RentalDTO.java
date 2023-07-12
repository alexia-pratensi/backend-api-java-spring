package fr.alexia.backendapi.DTO;

import java.util.Date;
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
	private Date createdAt;
	private Date updatedAt;
}

// public static RentalDTO fromEntity(Rental rental) {
// RentalDTO rentalDTO = new RentalDTO();
// rentalDTO.setId(rental.getId());
// rentalDTO.setName(rental.getName());
// rentalDTO.setSurface(rental.getSurface());
// rentalDTO.setPrice(rental.getPrice());
// rentalDTO.setPicture(rental.getPicture());
// rentalDTO.setDescription(rental.getDescription());
// rentalDTO.setOwnerId(rental.getOwner());
// rentalDTO.setCreatedAt(rental.getCreatedAt());
// rentalDTO.setUpdatedAt(rental.getUpdatedAt());
// return rentalDTO;
// }
