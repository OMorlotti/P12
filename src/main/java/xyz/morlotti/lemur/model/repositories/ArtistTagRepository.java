package xyz.morlotti.lemur.model.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.ArtistTag;

@Repository
public interface ArtistTagRepository extends JpaRepository<ArtistTag, Integer>
{
	@Query("SELECT at FROM lm_artisttag at WHERE at.artist.id = :artistId")
	public List<ArtistTag> findArtistTagByArtistId(@Param("artistId") Integer artistId);

	@Query("SELECT t FROM lm_tags t, lm_artisttag at WHERE at.tag.id = t.id AND at.artist.id = :artistId")
	public List<Tag> findTagsByArtistId(@Param("artistId") Integer artistId);
}
