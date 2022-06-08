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
	public void setTagsById(int artworkId, List<String> tagIds)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Artwork artwork = artworkRepository.findById(artworkId).orElseThrow(() -> new RuntimeException("Artwork `" + artworkId + "` not found"));

		/*------------------------------------------------------------------------------------------------------------*/

		// Function.identity() equals x -> x

		Map<Integer, ArtworkTag> tagIdToArtistTag = artworkTagRepository.findArtworkTagByArtworkId(artworkId).stream().collect(Collectors.toMap(x -> x.getTag().getId(), Function.identity()));

		Map<Integer, Tag> tadIdToTag = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		List<Tag> tags = artworkTagRepository.findTagsByArtworkId(artworkId);

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> existingTags = tags.stream().map(Tag::getId).collect(Collectors.toSet());

		Set<Integer> wantedTags = tagIds.stream().map(Integer::valueOf).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> toBeRemovedSet = new HashSet<>(existingTags);
		toBeRemovedSet.removeAll(wantedTags);

		Set<Integer> toBeAddedSet = new HashSet<>(wantedTags);
		toBeAddedSet.removeAll(existingTags);

		/*------------------------------------------------------------------------------------------------------------*/

		List<ArtworkTag> toBeRemovedList = toBeRemovedSet.stream().map(tagIdToArtistTag::get).collect(Collectors.toList());

		/*------------------------------------------------------------------------------------------------------------*/

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
