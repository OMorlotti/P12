package xyz.morlotti.lemur.controller_api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

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
}
