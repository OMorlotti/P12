package xyz.morlotti.woocommerce.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import xyz.morlotti.woocommerce.client.service.GitHub;
import xyz.morlotti.woocommerce.client.service.Shop;

@SpringBootApplication
@EnableFeignClients
public class Test implements CommandLineRunner
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	public Shop shop;

	@Autowired
	public GitHub gitHub;

	/*----------------------------------------------------------------------------------------------------------------*/

	public static void main(String[] args)
	{
		SpringApplication.run(Test.class, args);

		System.exit(0);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void run(String... args)
	{
		try
		{
			System.out.println(shop.getProducts());

			System.out.println(shop.getOrders());

			System.out.println(gitHub.me());

			System.out.println(gitHub.tree("master"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
