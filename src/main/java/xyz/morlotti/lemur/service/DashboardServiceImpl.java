package xyz.morlotti.lemur.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;

@Service
public class DashboardServiceImpl implements DashboardService
{
	@Autowired
	private WooCommerce wooCommerce;

	@Override
	public long countProducts()
	{
		return wooCommerce.countProducts();
	}

	@Override
	public List<Product> getProducts()
	{
		return wooCommerce.getProducts();
	}

	@Override
	public List<Product> getLastThreeProducts()
	{
		List<Product> result = new ArrayList<>();

		List<Product> list = wooCommerce.getProducts();

		for(int i = 0; i < Math.min(list.size(), 3); i++)
		{
			result.add(list.get(i));
		}

		return result;
	}
}
