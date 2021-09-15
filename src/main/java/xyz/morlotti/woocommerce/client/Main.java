package xyz.morlotti.woocommerce.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

public class Main
{
	private static final String BASE_URL = "http://localhost:8888/wp-json/wc/v3/";
	private static final String CONSUMER_KEY = "ck_bcbe3a8048193df865c10f4e2a7e2876845f179a";
	private static final String CONSUMER_SECRET = "cs_44aaac8f0d436f840c742a53f428006acf11c336";

	public static void main(String[] args)
	{
		System.exit(0);
	}

	@SpringBootApplication
	@EnableFeignClients // enable component scanning for interfaces that declare they are Feign clients
	public static class ExampleApplication
	{
		public static void main(String[] args)
		{
			SpringApplication.run(ExampleApplication.class, args);
		}
	}
}
