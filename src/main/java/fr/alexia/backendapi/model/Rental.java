package fr.alexia.backendapi.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "RENTALS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Rental {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	 private Long id;
	 
	 private String name;
	 
	 private int surface;
	 
	 private int price;
	 
	 private String picture;
	 
	 private String description;
	 // peut etre onetomany avec fetchType
	 @ManyToOne(cascade = CascadeType.ALL)
	 @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
	 private InternalUser ownerId;
	 
	 @CreationTimestamp
	 private Date created_at;
	
	 @UpdateTimestamp
	 private Date updated_at;
	 
    // Clé étrangère vers Message (relation One-to-Many)
    @OneToMany(mappedBy = "rentalId")
    private List<Message> messages;

}