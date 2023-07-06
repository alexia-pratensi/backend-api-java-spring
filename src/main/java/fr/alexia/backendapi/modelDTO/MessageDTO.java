package fr.alexia.backendapi.modelDTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDTO {
	
	private Long id;
	private Long rental_id;
	private	Long user_id;
	private String message;
	private Date created_at;
	private Date updated_at;
	
}
