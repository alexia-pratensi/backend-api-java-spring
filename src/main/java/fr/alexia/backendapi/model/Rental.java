//package fr.alexia.backendapi.model;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityListeners;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@EntityListeners(AuditingEntityListener.class)
//@Table(name = "RENTALS")
//public class Rental {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    
//    @Column(name = "name")
//    private String name;
//    
//    @Column(name = "surface")
//    private int surface;
//    
//    @Column(name = "price")
//    private int price;
//    
//    @Column(name = "picture")
//    private String picture;
//    
//    @Column(name = "description")
//    private String description;
//    
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id",  referencedColumnName = "id")
//    private InternalUser owner;
//    
////    @JsonFormat(pattern = "dd/MM/YYYY")
////    @CreationTimestamp
//	@Column(name = "created_at")
//    private LocalDate createdAt;
//    
////    @JsonFormat(pattern = "dd/MM/YYYY")
////    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDate updatedAt;
//}

package fr.alexia.backendapi.model;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "RENTALS")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "surface")
    private int surface;
    
    @Column(name = "price")
    private int price;
    
    @Column(name = "picture")
    private String picture;
    
    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id",  referencedColumnName = "id")
    private InternalUser owner;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
