package xyz.morlotti.lemur.controllers_api;

import java.util.List;
import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.service.ArtistsService;
import xyz.morlotti.lemur.controllers_api.bean.DataSource;

@RestController
public class ArtistsControllerAPI
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtistsService artistsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/api/artists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DataSource<Artist> artists()
	{
		return new DataSource<Artist>(artistsService.getArtists());
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/api/artists/{id}", method = RequestMethod.DELETE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> deleteTags(@PathVariable("id") int id)
	{
		try
		{
			artistsService.deleteArtist(id);
		}
		catch(RuntimeException e)
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}

		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(id));
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/api/artists/{id}/tags", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Tag> getTags(@PathVariable("id") int id)
	{
		return artistsService.getTagsById(id);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/api/artists/{id}/tags", method = RequestMethod.PUT, consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> setTags(@PathVariable("id") int id, @RequestBody String list)
	{
		List<String> ids = Arrays.asList(list.split(","));

		artistsService.setTagsById(id, ids);

		return ResponseEntity.status(HttpStatus.OK).body(String.valueOf(id));
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
