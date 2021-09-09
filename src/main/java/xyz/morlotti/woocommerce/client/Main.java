package xyz.morlotti.woocommerce.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;

public class Main
{
	private static final String BASE_URL = "http://localhost:8888/wp-json/wc/v3/";
	private static final String CONSUMER_KEY = "ck_bcbe3a8048193df865c10f4e2a7e2876845f179a";
	private static final String CONSUMER_SECRET = "cs_44aaac8f0d436f840c742a53f428006acf11c336";
	private static final String SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String VERSION = "1.0";

	public static void main(String[] args)
	{
		try
		{
			System.out.println(buildSignature("GET", "products"));
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		System.exit(0);
	}

	public static String buildSignature(String method, String urlSuffix) throws Exception
	{
		String nonce = "GdigHTLRTbL";
		String timestamp = "1631199059";

		String string = method.toUpperCase() + "&" + percentEncode(Main.BASE_URL + urlSuffix) + "&" + percentEncode(
			        percentEncode("oauth_consumer_key") + "=" + percentEncode(CONSUMER_KEY)
			+ "&" + percentEncode("oauth_nonce") + "=" + percentEncode(nonce)
	        + "&" + percentEncode("oauth_signature_method") + "=" + percentEncode(SIGNATURE_METHOD)
			+ "&" + percentEncode("oauth_timestamp") + "=" + percentEncode(timestamp)
			+ "&" + percentEncode("oauth_version") + "=" + percentEncode(VERSION)
		);

		System.out.println(string);

		return percentEncode(doSign(string, CONSUMER_SECRET + "&"));
	}

	public static String percentEncode(String url)
	{
		return URLEncoder.encode(url, StandardCharsets.UTF_8);
	}

	private static String doSign(String toSign, String keyString) throws Exception
	{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);

		byte[] bytes = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder().encodeToString(bytes);
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
