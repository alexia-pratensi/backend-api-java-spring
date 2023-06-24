package fr.alexia.backendapi.model;




import java.util.Date;

import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "MESSAGES")
public class Message {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	 @ManyToOne(optional = false)
	 @JoinColumn(name = "rental_id", referencedColumnName = "id", insertable = false, updatable = false)
	 private Rental rental;

	 @ManyToOne
	 @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
	 private InternalUser user;
	    
    private String message;
    
    @CreatedDate 
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
    
    @CreatedDate 
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Rental getRental() {
	    return rental;
	}

	public void setRental(Rental rentalId) {
		this.rental = rentalId;
	}

	public InternalUser getUser() {
	     return user;
	}

	public void setUser(InternalUser userId) {
		this.user = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	    
	    
}


//	 @ManyToOne
//	 @JoinColumn(name = "rental_id", referencedColumnName = "id")
//	 private Rental rentalId;
//
//	 @ManyToOne
//	 @JoinColumn(name = "user_id", referencedColumnName = "id")
//	 private InternalUser userId;