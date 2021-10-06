package xyz.morlotti.lemur.model.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Artwork;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Integer>
{
	@Query("SELECT a FROM lm_artworks a WHERE a.name = :name")
	public Optional<Artwork> findByFirstName(@Param("name") String name);
}
