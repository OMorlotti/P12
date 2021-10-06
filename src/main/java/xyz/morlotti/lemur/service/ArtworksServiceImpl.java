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
}
