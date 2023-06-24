package fr.alexia.backendapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "RENTALS")
public class Rental {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	
	 private Long id;
	 
	 private String name;
	 
	 private int surface;
	 
	 private int price;
	 
	 private String picture;
	 
	 private String description;
	 
	 @ManyToOne
	 @JoinColumn(name = "owner_id", referencedColumnName = "id", insertable = false, updatable = false)
	 private InternalUser ownerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSurface() {
		return surface;
	}

	public void setSurface(int surface) {
		this.surface = surface;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public InternalUser getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(InternalUser ownerId) {
		this.ownerId = ownerId;
	}
}