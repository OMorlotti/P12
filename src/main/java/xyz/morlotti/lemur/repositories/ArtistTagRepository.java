package xyz.morlotti.lemur.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.morlotti.lemur.model.bean.ArtistTag;

@Repository
public interface ArtistTagRepository extends JpaRepository<ArtistTag, Integer>
{

}
