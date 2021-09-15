package xyz.morlotti.woocommerce.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import xyz.morlotti.woocommerce.client.service.Products;

@SpringBootApplication
@EnableFeignClients
public class Main implements CommandLineRunner
{
	@Autowired
	public Products products;

	public static void main(String[] args)
	{
		SpringApplication.run(Main.class, args);

		System.exit(0);
	}

	@Override
	public void run(String... args) throws Exception
	{
		System.out.println(products.getProducts());
	}
}
