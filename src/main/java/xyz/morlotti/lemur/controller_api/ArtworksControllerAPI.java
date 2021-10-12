package xyz.morlotti.lemur.controller_api;

import java.util.List;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.service.ArtworksService;
import xyz.morlotti.lemur.controller_api.bean.DataSource;

@RestController
public class ArtworksControllerAPI
{
	@Autowired
	ArtworksService artworksService;

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
}
