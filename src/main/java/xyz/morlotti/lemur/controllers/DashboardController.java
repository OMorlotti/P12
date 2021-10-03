package xyz.morlotti.lemur.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.DashboardService;

@Controller
public class DashboardController
{
	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model)
	{
		model.addAttribute("lastThreeProducts", dashboardService.getLastThreeProducts());

		return "dashboard";
	}
}
