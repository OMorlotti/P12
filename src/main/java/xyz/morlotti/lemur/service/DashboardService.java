package xyz.morlotti.lemur.service;

import java.util.List;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;

public interface DashboardService
{
	long countProducts();

	List<Product> getProducts();

	List<Product> getLastThreeProducts();
}
