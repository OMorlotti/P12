package xyz.morlotti.lemur.service;

import java.util.List;

import xyz.morlotti.woocommerce.client.bean.Product;

public interface DashboardService
{
	List<Product> getProducts();

	List<Product> getLastThreeProducts();
}
