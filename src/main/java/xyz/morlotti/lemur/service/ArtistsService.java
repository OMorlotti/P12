package xyz.morlotti.lemur.service;

import xyz.morlotti.lemur.model.bean.Artist;

import java.util.List;

public interface ArtistsService
{
	List<Artist> getArtists();

	void addArtist(Artist artist);

	void updateArtist(Artist artist);

	void deleteArtist(int id);
}
