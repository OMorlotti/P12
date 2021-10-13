package xyz.morlotti.lemur.clients.woocommerce.service;

import java.util.List;

import xyz.morlotti.lemur.clients.woocommerce.bean.Product;

public interface WooCommerce
{
	/*----------------------------------------------------------------------------------------------------------------*/

	long countOrders();

	List<Object> getOrders();

	/*----------------------------------------------------------------------------------------------------------------*/

	long countProducts();

	List<Product> getProducts();

	/*----------------------------------------------------------------------------------------------------------------*/
}
