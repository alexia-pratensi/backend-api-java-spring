package fr.alexia.backendapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{

}
