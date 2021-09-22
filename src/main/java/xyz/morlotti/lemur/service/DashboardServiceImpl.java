package xyz.morlotti.lemur.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.woocommerce.client.bean.Product;
import xyz.morlotti.woocommerce.client.proxy.WooCommerceClients;

@Service
public class DashboardServiceImpl implements DashboardService
{
	@Autowired
	private WooCommerceClients wooCommerceClients;

	public List<Product> getProducts()
	{
		return wooCommerceClients.getProducts();
	}

	public List<Product> getLastThreeProducts()
	{
		List<Product> result = new ArrayList<>();

		List<Product> list = wooCommerceClients.getProducts();

		for(int i = 0; i < Math.min(list.size(), 3); i++)
		{
			result.add(list.get(i));
		}

		return result;
	}
}
