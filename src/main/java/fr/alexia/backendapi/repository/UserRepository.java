package fr.alexia.backendapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.alexia.backendapi.model.InternalUser;

@Repository
public interface UserRepository extends JpaRepository<InternalUser, Long> {

	Optional<InternalUser> findByName(String name);

	boolean existsByName(String name);
}
