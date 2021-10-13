package xyz.morlotti.lemur.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.bean.ArtistTag;
import xyz.morlotti.lemur.model.repositories.TagRepository;
import xyz.morlotti.lemur.model.repositories.ArtistRepository;
import xyz.morlotti.lemur.model.repositories.ArtistTagRepository;

@Service
public class ArtistsServiceImpl implements ArtistsService
{
	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	ArtistTagRepository artistTagRepository;

	@Autowired
	TagRepository tagRepository;

	@Override
	public long countArtists()
	{
		return artistRepository.count();
	}

	@Override
	public List<Artist> getArtists()
	{
		return artistRepository.findAll();
	}

	@Override
	public Optional<Artist> getArtistById(int id)
	{
		return artistRepository.findById(id);
	}

	@Override
	public void addArtist(Artist artist)
	{
		artistRepository.save(artist);
	}

	@Override
	public void addArtists(Iterable<Artist> artists)
	{
		artistRepository.saveAll(artists);
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

	@Override
	public List<Tag> getTagsById(int id)
	{
		return artistTagRepository.findTagsByArtistId(id);
	}

	@Override
	public void setTagsById(int id, List<String> ids)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Artist artist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist `" + id + "` not found"));

		Map<Integer, Tag> map = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		List<Tag> tags = artistTagRepository.findTagsByArtistId(id);

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> existingTags = tags.stream().map(x -> /**/ x.getId() /**/).collect(Collectors.toSet());

		Set<Integer> wantedTags = ids.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> toBeRemovedSet = new HashSet<>(existingTags);
		toBeRemovedSet.removeAll(wantedTags);

		Set<Integer> toBeAddedSet = new HashSet<>(wantedTags);
		toBeAddedSet.removeAll(existingTags);

		/*------------------------------------------------------------------------------------------------------------*/

		List<ArtistTag> toBeAddedList = toBeAddedSet.stream().map(x -> new ArtistTag(null, artist, map.get(x), null)).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

		artistTagRepository.deleteAllById(toBeRemovedSet);

		artistTagRepository.saveAll(toBeAddedList);

		/*------------------------------------------------------------------------------------------------------------*/
	}
}
