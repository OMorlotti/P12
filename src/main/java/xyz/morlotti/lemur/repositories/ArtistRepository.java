package xyz.morlotti.lemur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.morlotti.lemur.model.bean.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer>
{

}
