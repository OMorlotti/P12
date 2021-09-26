package xyz.morlotti.lemur.clients.woocommerce.service;

import java.util.List;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;

public interface WooCommerce
{
	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Object> getOrders();

	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Product> getProducts();

	/*----------------------------------------------------------------------------------------------------------------*/
}
