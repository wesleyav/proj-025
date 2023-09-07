package app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import app.entities.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query("SELECT a FROM Author a WHERE a.name = :name")
	Optional<Author> findByName(String name);

}
