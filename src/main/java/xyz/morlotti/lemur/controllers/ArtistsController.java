package xyz.morlotti.lemur.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ArtistsService;

@Controller
public class ArtistsController
{
	@Autowired
	ArtistsService artistsService;

	@RequestMapping(value = "/artists", method = RequestMethod.GET)
	public String artists()
	{
		return "artists";
	}
}
