package fr.alexia.backendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.alexia.backendapi.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

}
