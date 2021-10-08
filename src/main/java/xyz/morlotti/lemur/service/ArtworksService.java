package xyz.morlotti.lemur.service;

import java.util.List;

import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.bean.Tag;

public interface ArtworksService
{
	List<Artwork> getArtworks();

	void addArtwork(Artwork artwork);

	void updateArtwork(Artwork artwork);

	void deleteArtwork(int id);

	public List<Tag> getTagsById(int id);

	void setTagsById(int id, List<String> ids);
}
