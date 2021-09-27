package xyz.morlotti.lemur.clients.woocommerce.proxy;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.morlotti.lemur.model.bean.Artist;
import xyz.morlotti.lemur.clients.woocommerce.bean.Product;

@FeignClient(value = "woocommerce", url = "${woocommerce.base_url}", configuration = WooCommerceClientConfiguration.class)
public interface WooCommerceClient
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Object> getOrders();

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Product> getProducts();

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/artists", produces = MediaType.APPLICATION_JSON_VALUE)
	List<Artist> getArtists();

}
