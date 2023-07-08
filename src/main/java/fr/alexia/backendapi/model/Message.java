//package fr.alexia.backendapi.model;
//
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.util.Date;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.FetchType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Table(name = "MESSAGES")
//
//public class Message {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "rental_id", referencedColumnName = "id")
//    private Rental rental;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id",  referencedColumnName = "id")
//    private InternalUser user;
//    
//    @Column(name = "message")
//    private String message;
//    
//    @JsonFormat(pattern = "dd/MM/YYYY")
//    @CreationTimestamp
//	@Column(name = "created_at")
//    private LocalDate createdAt;
//    
//    @JsonFormat(pattern = "dd/MM/YYYY")
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDate updatedAt;
//}
package fr.alexia.backendapi.model;

import java.time.LocalDateTime;
import jakarta.persistence.FetchType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
//@EntityListeners(AuditingEntityListener.class)
@Table(name = "MESSAGES")

public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_id", referencedColumnName = "id")
    private Rental rental;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",  referencedColumnName = "id")
    private InternalUser user;
    
    @Column(name = "message")
    private String message;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}