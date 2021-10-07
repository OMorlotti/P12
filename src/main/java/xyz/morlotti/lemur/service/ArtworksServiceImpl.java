package xyz.morlotti.lemur.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.repositories.ArtworkRepository;

@Service
public class ArtworksServiceImpl implements ArtworksService
{
	@Autowired
	ArtworkRepository artworkRepository;

	@Override
	public List<Artwork> getArtworks()
	{
		return artworkRepository.findAll();
	}

	public void addArtwork(Artwork artwork)
	{
		artworkRepository.save(artwork);
	}

	public void updateArtwork(Artwork artwork)
	{
		Artwork existingArtwork = artworkRepository.findById(artwork.getId()).orElseThrow(() -> new RuntimeException("Artwork `" + artwork.getId() + "` not found"));

		existingArtwork.setName(artwork.getName());

		artworkRepository.save(existingArtwork);
	}

	public void deleteArtwork(int id)
	{
		Artwork existingArtwork = artworkRepository.findById(id).orElseThrow(() -> new RuntimeException("Artwork `" + id + "` not found"));

		artworkRepository.delete(existingArtwork);
	}
}
