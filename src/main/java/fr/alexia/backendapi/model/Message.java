package fr.alexia.backendapi.model;

import java.util.Date;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "MESSAGES")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "rental_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Rental rentalId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false, nullable = false)
    private InternalUser userId;

    private String message;

    @CreationTimestamp
    private Date created_at;

    @UpdateTimestamp
    private Date updated_at;

}


//	 @ManyToOne
//	 @JoinColumn(name = "rental_id", referencedColumnName = "id")
//	 private Rental rentalId;
//
//	 @ManyToOne
//	 @JoinColumn(name = "user_id", referencedColumnName = "id")
//	 private InternalUser userId;

//@CreatedDate
//@Temporal(TemporalType.TIMESTAMP)
//private Date created_at;
//
//@LastModifiedDate
//@Temporal(TemporalType.TIMESTAMP)
//private Date Updated_at;