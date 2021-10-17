package xyz.morlotti.lemur.controllers;

import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.service.TagsService;
import xyz.morlotti.lemur.service.ArtistsService;

@Controller
public class ArtistsController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtistsService artistsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	TagsService tagsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/artists", method = RequestMethod.GET)
	public String artists(Model model)
	{
		try
		{
			model.addAttribute("tags", tagsService.getTags());
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "artists";
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/artists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addUpdateArtists(@ModelAttribute("artist") Artist artist, Model model)
	{
		try
		{
			if(artist.getId() < 0)
			{
				// Add
				artistsService.addArtist(artist);
			}
			else
			{
				// Update
				artistsService.updateArtist(artist);
			}
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		try
		{
			model.addAttribute("tags", tagsService.getTags());
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "artists";
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
