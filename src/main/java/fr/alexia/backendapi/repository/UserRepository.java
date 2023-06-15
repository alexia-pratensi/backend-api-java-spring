package fr.alexia.backendapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

}
