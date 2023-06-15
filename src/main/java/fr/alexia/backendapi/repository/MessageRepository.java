package fr.alexia.backendapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.Rental;

@Repository
public interface MessageRepository extends CrudRepository<Rental, Long>{

}
