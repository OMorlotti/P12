package xyz.morlotti.woocommerce.client.service;

import xyz.morlotti.woocommerce.client.bean.Product;

import java.util.List;

public interface Products
{
	public List<Object> getOrders();

	public List<Product> getProducts();
}
