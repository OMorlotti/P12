package xyz.morlotti.woocommerce.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import xyz.morlotti.lemur.clients.github.service.GitHub;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;

//@SpringBootApplication
//@EnableFeignClients
public class Test implements CommandLineRunner
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	public WooCommerce shop;

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

			System.out.println(gitHub.fileTree("master"));

			System.out.println(gitHub.versions("/pages/02.articles/theorie-lagrangienne-des-champs/item.fr.md"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
