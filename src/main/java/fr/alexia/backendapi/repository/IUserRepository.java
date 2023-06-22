package fr.alexia.backendapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.InternalUser;



@Repository
public interface IUserRepository extends JpaRepository<InternalUser, Integer> {

	List<InternalUser> findByUsername(String username);

}
