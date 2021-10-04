package xyz.morlotti.lemur.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.bean.ArtistTag;

@Repository
public interface ArtistTagRepository extends JpaRepository<ArtistTag, Integer>
{
	@Query("SELECT a FROM lm_artists a, lm_artisttag at WHERE at.artist.id = a.id AND at.tag.id = :tagId")
	public List<Artist> findArtistsByTagId(@Param("tagId") String tagId);

	@Query("SELECT t FROM lm_tags t, lm_artisttag at WHERE at.tag.id = t.id AND at.artist.id = :artistId")
	public List<Tag> findTagsByArtistsId(@Param("artistId") String artistId);

	@Query("SELECT t FROM lm_tags t, lm_artisttag at WHERE at.tag.id = t.id AND at.artist.pseudo = :artistPseudo")
	public List<Tag> findTagsByArtistsPseudo(@Param("artistPseudo") String artistPseudo);
}
