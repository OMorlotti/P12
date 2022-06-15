package xyz.morlotti.lemur.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.bean.ArtistTag;
import xyz.morlotti.lemur.model.repositories.TagRepository;
import xyz.morlotti.lemur.model.repositories.ArtistRepository;
import xyz.morlotti.lemur.model.repositories.ArtistTagRepository;

@Service
@Transactional
public class ArtistsServiceImpl implements ArtistsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtistRepository artistRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtistTagRepository artistTagRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	TagRepository tagRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public long countArtists()
	{
		return artistRepository.count();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Artist> getArtists()
	{
		return artistRepository.findAll();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Optional<Artist> getArtistById(int id)
	{
		return artistRepository.findById(id);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addArtist(Artist artist)
	{
		artistRepository.save(artist);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addArtists(Iterable<Artist> artists)
	{
		artistRepository.saveAll(artists);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
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

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void deleteArtist(int id)
	{
		Artist existingArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist `" + id + "` not found"));

		artistRepository.delete(existingArtist);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Tag> getTagsByArtistId(int id)
	{
		return artistTagRepository.findTagsByArtistId(id);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void setTagsForArtistId(int artistId, List<String> tagIds) // tagIds la liste complète des tags que l'on veut associer à l'artiste
	{
		/*------------------------------------------------------------------------------------------------------------*/

		// On trouve l'artiste d'id "artistId"

		Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new RuntimeException("Artist `" + artistId + "` not found"));

		/*------------------------------------------------------------------------------------------------------------*/

		// Function.identity() equals x -> x

		// on ne considère que l'artiste d'id "artistId", connaissant le "tagId", on veut obtenir le bridge "ArtistTag" avec une map

		Map<Integer, ArtistTag> tagIdToArtistTag = artistTagRepository.findArtistTagByArtistId(artistId).stream().collect(Collectors.toMap(x -> x.getTag().getId(), Function.identity()));

		// connaissant le "tagId", on veut obtenir le tag correspondant

		Map<Integer, Tag> tadIdToTag = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		// liste de tous les tags appartenant à l'artiste d'id "artistId"

		List<Tag> tags = artistTagRepository.findTagsByArtistId(artistId);

		/*------------------------------------------------------------------------------------------------------------*/

		// Tag::getId equals x -> x.getId()
		// Integer::valueOf equals x -> x.valueOf()

		// on récupère depuis la liste "tags" ci-dessus, uniquement les id dans un set => tous les id de tous les tags déjà associés à l'artiste

		Set<Integer> existingTags = tags.stream().map(Tag::getId).collect(Collectors.toSet());

		// on veut transformer la liste de tagId en entrée de la method sous forme de String en list d'integers => tous les id que l'on veut associer à l'artiste

		Set<Integer> wantedTags = tagIds.stream().map(Integer::valueOf).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		// on calcul les tagId des bridges "ArtistTag" que l'on doit supprimer
		Set<Integer> toBeRemovedSet = new HashSet<>(existingTags);
		toBeRemovedSet.removeAll(wantedTags);

		// on calcul les tagId des bridges "ArtistTag" que l'on doit ajouter
		Set<Integer> toBeAddedSet = new HashSet<>(wantedTags);
		toBeAddedSet.removeAll(existingTags);

		/*------------------------------------------------------------------------------------------------------------*/

		// on transform le set de tagId à supprimer en set de ArtistTag à supprimer avec la map tagIdToArtistTag

		List<ArtistTag> toBeRemovedList = toBeRemovedSet.stream().map(tagIdToArtistTag::get).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

		// on fabrique la liste de ArtistTag à ajouter à partir du set des tagId que l'on doit ajouter avec la map tadIdToTag

		List<ArtistTag> toBeAddedList = toBeAddedSet.stream().map(x -> new ArtistTag(null, artist, tadIdToTag.get(x), null)).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

		System.out.println("Remove: " + toBeRemovedList);

		System.out.println("Add: " + toBeAddedList);

		artistTagRepository.deleteAll(toBeRemovedList);

		artistTagRepository.saveAll(toBeAddedList);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
