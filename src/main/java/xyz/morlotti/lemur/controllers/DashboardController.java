package xyz.morlotti.lemur.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ArtistsService;
import xyz.morlotti.lemur.service.ArtworksService;
import xyz.morlotti.lemur.service.DashboardService;

@Controller
public class DashboardController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtistsService artistsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ArtworksService artworksService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	DashboardService dashboardService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model)
	{
		try
		{
			model.addAttribute("numberOfArtists", artistsService.countArtists());
			model.addAttribute("numberOfArtworks", artworksService.countArtworks());
			model.addAttribute("numberOfArtworksInShop", dashboardService.countProducts());
			model.addAttribute("lastThreeProducts", dashboardService.getLastThreeProducts());

			return "dashboard";
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());

			return "error";
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
