package xyz.morlotti.lemur.service;

import java.util.List;
import java.util.Optional;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;

public interface ArtworksService
{
	long countArtworks();

	List<Artwork> getArtworks();

	Optional<Artwork> getArtworkById(int id);

	void addArtwork(Artwork artwork);

	void addArtworks(Iterable<Artwork> artworks);

	void updateArtwork(Artwork artwork);

	void deleteArtwork(int id);

	List<Tag> getTagsById(int id);

	void setTagsById(int id, List<String> ids);
}
