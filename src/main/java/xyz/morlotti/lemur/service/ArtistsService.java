package xyz.morlotti.lemur.service;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.bean.Tag;

import java.util.List;

public interface ArtistsService
{
	List<Artist> getArtists();

	void addArtist(Artist artist);

	void updateArtist(Artist artist);

	void deleteArtist(int id);

	public List<Tag> getTagsById(int id);

	void setTagsById(int id, List<String> ids);
}
