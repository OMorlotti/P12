package xyz.morlotti.lemur.clients.woocommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.clients.woocommerce.bean.Product;
import xyz.morlotti.lemur.clients.woocommerce.proxy.WooCommerceClient;

@Service
public class WooCommerceImpl implements WooCommerce
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private WooCommerceClient wooCommerceClients;

	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Object> getOrders()
	{
		return wooCommerceClients.getOrders();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Product> getProducts()
	{
		return wooCommerceClients.getProducts();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Artist> getArtists()
	{
		return wooCommerceClients.getArtists();
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
