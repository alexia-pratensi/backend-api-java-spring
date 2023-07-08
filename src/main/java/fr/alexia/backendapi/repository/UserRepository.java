package fr.alexia.backendapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.InternalUser;

@Repository
public interface UserRepository extends JpaRepository<InternalUser, Long>{

}
