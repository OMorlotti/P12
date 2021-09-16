package xyz.morlotti.woocommerce.client.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "woocommerce", url = "${woocommerce.base_url}", configuration = WooCommerceClientsConfiguration.class)
public interface WooCommerceClients
{
	@RequestMapping(method = RequestMethod.GET, value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Object> getProducts();

	@RequestMapping(method = RequestMethod.GET, value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Object> getDashboard();
}
