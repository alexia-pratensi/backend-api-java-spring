package fr.alexia.backendapi.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.InternalUser;

@Repository
public interface IUserRepository extends CrudRepository<InternalUser, Long>{

}
