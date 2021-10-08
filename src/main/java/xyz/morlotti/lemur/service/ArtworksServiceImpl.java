package xyz.morlotti.lemur.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.repositories.ArtworkRepository;
import xyz.morlotti.lemur.model.repositories.ArtworkTagRepository;
import xyz.morlotti.lemur.model.repositories.TagRepository;

@Service
public class ArtworksServiceImpl implements ArtworksService
{
	@Autowired
	ArtworkRepository artworkRepository;

	@Autowired
	ArtworkTagRepository artworkTagRepository;

	@Autowired
	TagRepository tagRepository;

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

	public List<Tag> getTagsById(int id)
	{
		return artworkTagRepository.findTagsByArtworkId(id);
	}

	@Override
	public void setTagsById(int id, List<String> ids)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> existingTags = artworkTagRepository.findTagsByArtworkId(id).stream().map(x -> x.getId()).collect(Collectors.toSet());

		Set<Integer> wantedTags = ids.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		Map<Integer, Tag> map = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		/*------------------------------------------------------------------------------------------------------------*/
	}
}
