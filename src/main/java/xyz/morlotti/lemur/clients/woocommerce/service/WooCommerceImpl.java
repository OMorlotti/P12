package xyz.morlotti.lemur.clients.woocommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.proxy.WooCommerceClient;

@Service
public class WooCommerceImpl implements WooCommerce
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private WooCommerceClient wooCommerceClients;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public long countOrders()
	{
		return wooCommerceClients.getOrders().size();
	}

	public List<Object> getOrders()
	{
		return wooCommerceClients.getOrders();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public long countProducts()
	{
		return wooCommerceClients.getProducts().size();
	}


	@Override
	public List<Product> getProducts()
	{
		return wooCommerceClients.getProducts();
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
