package xyz.morlotti.lemur.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.repositories.ArtistRepository;

import java.util.List;

@Service
public class ArtistsServiceImpl implements ArtistsService
{
	@Autowired
	ArtistRepository artistRepository;

	@Override
	public List<Artist> getArtists()
	{
		return artistRepository.findAll();
	}

	public void addArtist(Artist artist)
	{
		artistRepository.save(artist);
	}

	public void updateArtist(Artist artist)
	{
		Artist existingArtist = artistRepository.findById(artist.getId()).orElseThrow(() -> new RuntimeException("Artist `" + artist.getId() + "` not found"));

		existingArtist.setFirstName(artist.getFirstName());
		existingArtist.setLastName(artist.getLastName());
		existingArtist.setDescription(artist.getDescription());
		existingArtist.setEmail(artist.getEmail());
		existingArtist.setPseudo(artist.getPseudo());
		existingArtist.setYearOfBirth(artist.getYearOfBirth());
		existingArtist.setYearOfDeath(artist.getYearOfDeath());

		artistRepository.save(existingArtist);
	}

	public void deleteArtist(int id)
	{
		Artist existingArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist `" + id + "` not found"));

		artistRepository.delete(existingArtist);
	}
}
