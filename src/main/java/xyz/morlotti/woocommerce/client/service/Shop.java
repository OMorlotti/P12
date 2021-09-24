package xyz.morlotti.woocommerce.client.service;

import java.util.List;

import xyz.morlotti.woocommerce.client.bean.Product;

public interface Shop
{
	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Object> getOrders();

	/*----------------------------------------------------------------------------------------------------------------*/

	public List<Product> getProducts();

	/*----------------------------------------------------------------------------------------------------------------*/
}
