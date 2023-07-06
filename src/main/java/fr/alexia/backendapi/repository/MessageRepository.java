package fr.alexia.backendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import fr.alexia.backendapi.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{

}
