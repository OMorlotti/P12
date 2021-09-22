package xyz.morlotti.lemur.service;

import xyz.morlotti.woocommerce.client.bean.Product;

import java.util.List;

public interface DashboardService
{
	List<Product> getProducts();

	List<Product> getLastThreeProducts();
}
