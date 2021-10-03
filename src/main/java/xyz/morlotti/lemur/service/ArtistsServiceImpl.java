package xyz.morlotti.lemur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.repositories.ArtistRepository;

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
}
