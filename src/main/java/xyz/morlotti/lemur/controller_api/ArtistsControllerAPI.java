package xyz.morlotti.lemur.controller_api;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.service.ArtistsService;
import xyz.morlotti.lemur.controller_api.bean.DataSource;

@RestController
public class ArtistsControllerAPI
{
	@Autowired
	ArtistsService artistsService;

	@RequestMapping(value = "/api/artists", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public DataSource<Artist> artists()
	{
		return new DataSource<Artist>(artistsService.getArtists());
	}

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
}
