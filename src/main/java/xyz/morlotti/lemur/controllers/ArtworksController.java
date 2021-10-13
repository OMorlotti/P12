package xyz.morlotti.lemur.controllers;

import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.model.bean.Artwork;
import xyz.morlotti.lemur.service.TagsService;
import xyz.morlotti.lemur.service.ArtistsService;
import xyz.morlotti.lemur.service.ArtworksService;

import java.util.Optional;

@Controller
public class ArtworksController
{
	@Autowired
	ArtworksService artworksService;

	@Autowired
	ArtistsService artistsService;

	@Autowired
	TagsService tagsService;

	@RequestMapping(value = "/artworks", method = RequestMethod.GET)
	public String artworks(Model model)
	{
		model.addAttribute("artists", artistsService.getArtists());

		model.addAttribute("tags", tagsService.getTags());

		return "artworks";
	}

	@RequestMapping(value = "/artworks", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addUpdateArtists(@ModelAttribute("artwork") Artwork artwork, Model model)
	{
		try
		{
			if(artwork.getId() < 0)
			{
				// Add
				artworksService.addArtwork(artwork);
			}
			else
			{
				// Update
				artworksService.updateArtwork(artwork);
			}
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		model.addAttribute("artists", artistsService.getArtists());

		model.addAttribute("tags", tagsService.getTags());

		return "artworks";
	}
}
