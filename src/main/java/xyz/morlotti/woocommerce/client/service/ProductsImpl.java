package xyz.morlotti.woocommerce.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.morlotti.woocommerce.client.proxy.WooCommerceClients;

import java.util.List;

@Service
public class ProductsImpl implements Products
{
	@Autowired
	private WooCommerceClients wooCommerceClients;

	public List<Object> getProducts()
	{
		return wooCommerceClients.getProducts();
	}
}
