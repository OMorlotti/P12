package xyz.morlotti.lemur.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController
{
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home()
	{
		return "dashboard";
	}
}
