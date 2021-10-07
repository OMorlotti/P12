package xyz.morlotti.lemur.service;

import java.util.List;

import xyz.morlotti.lemur.model.bean.Artwork;

public interface ArtworksService
{
	List<Artwork> getArtworks();

	void addArtwork(Artwork artwork);

	void updateArtwork(Artwork artwork);

	void deleteArtwork(int id);
}
