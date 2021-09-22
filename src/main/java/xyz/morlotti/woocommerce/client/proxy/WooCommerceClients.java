package xyz.morlotti.woocommerce.client.proxy;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.morlotti.woocommerce.client.bean.Product;

@FeignClient(value = "woocommerce", url = "${woocommerce.base_url}", configuration = WooCommerceClientsConfiguration.class)
public interface WooCommerceClients
{
	@RequestMapping(method = RequestMethod.GET, value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Object> getOrders();

	@RequestMapping(method = RequestMethod.GET, value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Product> getProducts();
}
