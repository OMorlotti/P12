package xyz.morlotti.lemur.controller_api;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.service.ArtworksService;
import xyz.morlotti.lemur.controller_api.bean.DataSource;
import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;

@RestController
public class ArtworksControllerAPI
{
	@Autowired
	ArtworksService artworksService;

	@Autowired
	WooCommerce wooCommerce;

	@RequestMapping(value = "/api/artworks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DataSource<Artwork> artworks()
	{
		return new DataSource<Artwork>(artworksService.getArtworks());
	}

	@RequestMapping(value = "/api/artworks/{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteArtworks(@PathVariable("id") int id)
	{
		try
		{
			artworksService.deleteArtwork(id);
		}
		catch(RuntimeException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(id));
	}

	@RequestMapping(value = "/api/artworks/{id}/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tag> getTags(@PathVariable("id") int id)
	{
		return artworksService.getTagsById(id);
	}

	@RequestMapping(value = "/api/artworks/{id}/tags", method = RequestMethod.PUT, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setTags(@PathVariable("id") int id, @RequestBody String list)
	{
		List<String> ids = Arrays.asList(list.split(","));

		artworksService.setTagsById(id, ids);

		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(id));
	}

	@RequestMapping(value = "/api/artworks/synchronize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Artwork> synchronize()
	{
		List<Product> products = wooCommerce.getProducts();

		List<Artwork> artworks = artworksService.getArtworks();

		Set<Integer> alreadyImportedArtworks = artworks.stream().filter(x -> x.getWcId() != null).map(x -> x.getWcId()).collect(Collectors.toSet());

		List<Artwork> toBeAddedArtworks = new ArrayList<>();

		for(Product product: products)
		{
			if(!alreadyImportedArtworks.contains(product.getId()))
			{
				Artwork artwork = new Artwork();

				artwork.setWcId(product.getId());
				artwork.setName(product.getName());
				artwork.setDescription(product.getDescription().replace("<p>", "").replace("</p>", "").replace("\n", " ").trim());

				toBeAddedArtworks.add(artwork);
			}
		}

		artworksService.addArtworks(toBeAddedArtworks);

		return toBeAddedArtworks;
	}
}
