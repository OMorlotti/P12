package xyz.morlotti.lemur.controller_api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xyz.morlotti.lemur.controller_api.bean.DataSource;
import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.service.ArtistsService;

import java.util.List;

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
}
