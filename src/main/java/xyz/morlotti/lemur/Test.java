package xyz.morlotti.lemur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import xyz.morlotti.lemur.clients.github.service.GitHub;
import xyz.morlotti.lemur.clients.woocommerce.service.WooCommerce;
import xyz.morlotti.lemur.service.DocumentsService;

//@SpringBootApplication
//@EnableFeignClients
public class Test implements CommandLineRunner
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	public WooCommerce shop;

	@Autowired
	public DocumentsService documentsService;

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
			//System.out.println(shop.getProducts());

			//System.out.println(shop.getOrders());

			//System.out.println(gitHub.me());

			System.out.println(documentsService.getTree("master").get("/"));

			System.out.println(documentsService.getTree("master").get("/pages/02.articles/theorie-lagrangienne-des-champs/item.fr.md"));

			//System.out.println(gitHub.versions("/pages/02.articles/theorie-lagrangienne-des-champs/item.fr.md"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
