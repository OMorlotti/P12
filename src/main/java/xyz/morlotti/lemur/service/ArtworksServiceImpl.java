package xyz.morlotti.lemur.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.transaction.annotation.Transactional;
import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.bean.ArtworkTag;
import xyz.morlotti.lemur.model.repositories.TagRepository;
import xyz.morlotti.lemur.model.repositories.ArtworkRepository;
import xyz.morlotti.lemur.model.repositories.ArtworkTagRepository;

@Service
@Transactional
public class ArtworksServiceImpl implements ArtworksService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtworkRepository artworkRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtworkTagRepository artworkTagRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	TagRepository tagRepository;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	WooCommerce wooCommerce;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Artwork> getArtworks()
	{
		return artworkRepository.findAll();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public long countArtworks()
	{
		return artworkRepository.count();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Optional<Artwork> getArtworkById(int id)
	{
		return artworkRepository.findById(id);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addArtwork(Artwork artwork)
	{
		artworkRepository.save(artwork);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addArtworks(Iterable<Artwork> artworks)
	{
		artworkRepository.saveAll(artworks);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void updateArtwork(Artwork artwork)
	{
		Artwork existingArtwork = artworkRepository.findById(artwork.getId()).orElseThrow(() -> new RuntimeException("Artwork `" + artwork.getId() + "` not found"));

		existingArtwork.setName(artwork.getName());
		existingArtwork.setArtist(artwork.getArtist());
		existingArtwork.setDescription(artwork.getDescription());

		artworkRepository.save(existingArtwork);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void deleteArtwork(int id)
	{
		Artwork existingArtwork = artworkRepository.findById(id).orElseThrow(() -> new RuntimeException("Artwork `" + id + "` not found"));

		artworkRepository.delete(existingArtwork);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Tag> getTagsById(int id)
	{
		return artworkTagRepository.findTagsByArtworkId(id);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void setTagsById(int artworkId, List<String> tagIds) // tagIds la liste complète des tags que l'on veut associer à l'artwork
	{
		/*------------------------------------------------------------------------------------------------------------*/

		// On trouve l'artwork d'id "artworkId"

		Artwork artwork = artworkRepository.findById(artworkId).orElseThrow(() -> new RuntimeException("Artwork `" + artworkId + "` not found"));

		/*------------------------------------------------------------------------------------------------------------*/

		// Function.identity() equals x -> x

		// on ne considère que l'artwork d'id "artworkId", connaissant le "tagId", on veut obtenir le bridge "ArtworkTag" avec une map

		Map<Integer, ArtworkTag> tagIdToArtworkTag = artworkTagRepository.findArtworkTagByArtworkId(artworkId).stream().collect(Collectors.toMap(x -> x.getTag().getId(), Function.identity()));

		// connaissant le "tagId", on veut obtenir le tag correspondant

		Map<Integer, Tag> tadIdToTag = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		// liste de tous les tags appartenant à l'artiste d'id "artworkId"

		List<Tag> tags = artworkTagRepository.findTagsByArtworkId(artworkId);

		/*------------------------------------------------------------------------------------------------------------*/

		// Tag::getId equals x -> x.getId()
		// Integer::valueOf equals x -> x.valueOf()

		// on récupère depuis la liste "tags" ci-dessus, uniquement les id dans un set => tous les id de tous les tags déjà associés à l'artwork

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

		// on transform le set de tagId à supprimer en set de ArtistTag à supprimer avec la map tagIdToArtworkTag

		List<ArtworkTag> toBeRemovedList = toBeRemovedSet.stream().map(tagIdToArtworkTag::get).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

		// on fabrique la liste de ArtistTag à ajouter à partir du set des tagId que l'on doit ajouter avec la map tadIdToTag

		List<ArtworkTag> toBeAddedList = toBeAddedSet.stream().map(x -> new ArtworkTag(null, artwork, tadIdToTag.get(x), null)).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

		artworkTagRepository.deleteAllById(toBeRemovedSet);

		artworkTagRepository.saveAll(toBeAddedList);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<Artwork> synchronize()
	{
		/*------------------------------------------------------------------------------------------------------------*/

		List<Product> products = wooCommerce.getProducts();

		List<Artwork> artworks = /**/this/**/.getArtworks();

		Set<Integer> alreadyImportedArtworks = artworks.stream().filter(x -> x.getWcId() != null).map(x -> x.getWcId()).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		List<Artwork> toBeAddedArtworks = new ArrayList<>();

		for(Product product: products)
		{
			if(!alreadyImportedArtworks.contains(product.getId()))
			{
				Artwork artwork = new Artwork();

				artwork.setWcId(product.getId());
				artwork.setWcPermalink(product.getPermalink());
				artwork.setName(product.getName());
				artwork.setDescription(product.getDescription().replace("<p>", "").replace("</p>", "").replace("\n", " ").trim());

				toBeAddedArtworks.add(artwork);
			}
		}

		/*------------------------------------------------------------------------------------------------------------*/

		this.addArtworks(toBeAddedArtworks);

		/*------------------------------------------------------------------------------------------------------------*/

		return toBeAddedArtworks;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
