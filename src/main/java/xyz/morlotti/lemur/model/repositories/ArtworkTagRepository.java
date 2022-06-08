package xyz.morlotti.lemur.model.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.ArtworkTag;

@Repository
public interface ArtworkTagRepository extends JpaRepository<ArtworkTag, Integer>
{
	@Query("SELECT at FROM lm_artworktag at WHERE at.artwork.id = :artworkId")
	public List<ArtworkTag> findArtworkTagByArtworkId(@Param("artworkId") int artworkId);

	@Query("SELECT t FROM lm_tags t, lm_artworktag at WHERE at.tag.id = t.id AND at.artwork.id = :artworkId")
	public List<Tag> findTagsByArtworkId(@Param("artworkId") int artwork);
}
