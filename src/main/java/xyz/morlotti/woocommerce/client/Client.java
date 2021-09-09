package xyz.morlotti.woocommerce.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class Client
{
	@FeignClient(value = "jplaceholder", url = "https://jsonplaceholder.typicode.com/") // declare a Feign client using the @FeignClient annotation:
	public interface JSONPlaceHolderClient<Post> //configure a client to read from the JSONPlaceHolder APIs.
	{

		@RequestMapping(method = RequestMethod.GET, value = "/posts")
		List<Post> getPosts();

		@RequestMapping(method = RequestMethod.GET, value = "/posts/{postId}", produces = "application/json")
		Post getPostById(@PathVariable("postId") Long postId);
	}
}
