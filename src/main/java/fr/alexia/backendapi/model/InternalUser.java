package fr.alexia.backendapi.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "USERS", indexes = {@Index(name = "USERS_index", columnList = "email", unique = true)})
@AllArgsConstructor
@NoArgsConstructor
@Data
public class InternalUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name")
	private String username;
	
	private String email;
	
	private String password;
	
	@CreationTimestamp
	private Date created_at;
	
	@UpdateTimestamp
	private Date updated_at;
	
	  // Clé étrangère vers Rental (relation One-to-Many)
    @OneToMany(mappedBy = "ownerId")
    private List<Rental> rentals;

    // Clé étrangère vers Message (relation One-to-Many)
    @OneToMany(mappedBy = "userId")
    private List<Message> messages;
	
}
