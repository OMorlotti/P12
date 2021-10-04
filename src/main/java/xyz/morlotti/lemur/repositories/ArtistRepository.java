package xyz.morlotti.lemur.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>
{
	@Query("SELECT a FROM lm_artists a WHERE a.firstName = :firstName")
	public List<Artist> findByFirstName(@Param("firstName") String firstName);

	@Query("SELECT a FROM lm_artists a WHERE a.firstName = :lastName")
	public List<Artist> findByLastName(@Param("lastName") String lastName);

	@Query("SELECT a FROM lm_artists a WHERE a.pseudo = :pseudo")
	public Optional<Artist> findByPseudo(@Param("pseudo") String pseudo);

	@Query("SELECT a FROM lm_artists a WHERE a.email = :email")
	public Optional<Artist> findByEmail(@Param("email") String email);
}
