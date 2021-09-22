package xyz.morlotti.lemur.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.morlotti.lemur.service.DashboardService;
import xyz.morlotti.woocommerce.client.bean.Product;

@RestController
public class DashboardController
{
	@Autowired
	DashboardService dashboardService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home()
	{
		return "dashboard";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public Iterable<Product> listProducts()
	{
		return listProducts();
	}
}
