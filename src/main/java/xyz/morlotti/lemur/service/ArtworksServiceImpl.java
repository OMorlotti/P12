package xyz.morlotti.lemur.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.model.bean.ArtworkTag;
import xyz.morlotti.lemur.model.repositories.TagRepository;
import xyz.morlotti.lemur.model.repositories.ArtworkRepository;
import xyz.morlotti.lemur.model.repositories.ArtworkTagRepository;

@Service
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
	public void setTagsById(int id, List<String> ids)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Artwork artwork = artworkRepository.findById(id).orElseThrow(() -> new RuntimeException("Artwork `" + id + "` not found"));

		Map<Integer, Tag> map = tagRepository.findAll().stream().collect(Collectors.toMap(Tag::getId, Function.identity()));

		List<Tag> tags = artworkTagRepository.findTagsByArtworkId(id);

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> existingTags = tags.stream().map(x -> /**/ x.getId() /**/).collect(Collectors.toSet());

		Set<Integer> wantedTags = ids.stream().map(x -> Integer.valueOf(x)).collect(Collectors.toSet());

		/*------------------------------------------------------------------------------------------------------------*/

		Set<Integer> toBeRemovedSet = new HashSet<>(existingTags);
		toBeRemovedSet.removeAll(wantedTags);

		Set<Integer> toBeAddedSet = new HashSet<>(wantedTags);
		toBeAddedSet.removeAll(existingTags);

		/*------------------------------------------------------------------------------------------------------------*/

		List<ArtworkTag> toBeAddedList = toBeAddedSet.stream().map(x -> new ArtworkTag(null, artwork, map.get(x), null)).collect(Collectors.toList());

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
