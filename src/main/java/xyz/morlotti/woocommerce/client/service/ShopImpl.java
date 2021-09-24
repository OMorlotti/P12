package xyz.morlotti.woocommerce.client.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.woocommerce.client.bean.Product;
import xyz.morlotti.woocommerce.client.proxy.WooCommerceClient;

@Service
public class ShopImpl implements Shop
{
	@Autowired
	private WooCommerceClient wooCommerceClients;

	public List<Object> getOrders()
	{
		return wooCommerceClients.getOrders();
	}

	public List<Product> getProducts()
	{
		return wooCommerceClients.getProducts();
	}
}
