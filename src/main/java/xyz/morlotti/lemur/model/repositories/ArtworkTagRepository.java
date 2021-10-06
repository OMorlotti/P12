package xyz.morlotti.lemur.model.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.bean.ArtworkTag;

@Repository
public interface ArtworkTagRepository extends JpaRepository<ArtworkTag, Integer>
{
	@Query("SELECT a FROM lm_artworks a, lm_artworktag at WHERE at.artwork.id = a.id AND at.tag.id = :tagId")
	public List<Artwork> findArtistsByTagId(@Param("tagId") String tagId);

	@Query("SELECT t FROM lm_tags t, lm_artworktag at WHERE at.tag.id = t.id AND at.artwork.id = :artworkId")
	public List<Tag> findTagsByArtistsId(@Param("artworkId") String artwork);
}
