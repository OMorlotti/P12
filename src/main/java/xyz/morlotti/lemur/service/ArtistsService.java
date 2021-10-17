package xyz.morlotti.lemur.service;

import java.util.List;
import java.util.Optional;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artist;

public interface ArtistsService
{
	long countArtists();

	List<Artist> getArtists();

	Optional<Artist> getArtistById(int id);

	void addArtist(Artist artist);

	void addArtists(Iterable<Artist> artists);

	void updateArtist(Artist artist);

	void deleteArtist(int id);

	List<Tag> getTagsById(int id);

	void setTagsById(int id, List<String> ids);
}
