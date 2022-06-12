package xyz.morlotti.lemur.clients.woocommerce.proxy;

import java.util.Base64;
import java.util.Map;
import java.util.Random;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import feign.RequestInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ConfigService;

public class WooCommerceClientConfiguration
{
	/*----------------------------------------------------------------------------------------------------------------*/

	private static final String NONCE_BASE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	/*----------------------------------------------------------------------------------------------------------------*/

	private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String OAUTH_VERSION = "1.0";

	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${woocommerce.base_url}")
	private String baseUrl;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public RequestInterceptor requestInterceptor()
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Map<String, String> config = configService.getConfig();

		String consumerKey = config.getOrDefault("woocommerce_consumer_key", "");
		String consumerSecret = config.getOrDefault("woocommerce_consumer_secret", "");

		/*------------------------------------------------------------------------------------------------------------*/

		return requestTemplate -> {

			/*--------------------------------------------------------------------------------------------------------*/

			String nonce = generateNonce(11);

			String timestamp = generateTimestamp();

			/*--------------------------------------------------------------------------------------------------------*/

			String url = (baseUrl + requestTemplate.url()).replaceAll("(?<!:)//", "/");

			/*--------------------------------------------------------------------------------------------------------*/

			String signature;

			try
			{
				signature = buildSignature(requestTemplate.method(), url, consumerKey, consumerSecret, nonce, timestamp);
			}
			catch(Exception e)
			{
				signature = /*--------------------------*/ "" /*--------------------------*/;
			}

			/*--------------------------------------------------------------------------------------------------------*/

 			String header = buildHeader(consumerKey, nonce, timestamp, signature);

			/*--------------------------------------------------------------------------------------------------------*/

			requestTemplate.header("Authorization", header);

			/*--------------------------------------------------------------------------------------------------------*/
		};

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String buildHeader(String consumerKey, String nonce, String timestamp, String signature)
	{
		StringBuilder stringBuilder = new StringBuilder("OAuth ");

		stringBuilder.append("oauth_consumer_key=\"").append(quoteEscape(consumerKey)).append("\",")
		             .append("oauth_nonce=\"").append(quoteEscape(nonce)).append("\",")
		             .append("oauth_signature=\"").append(quoteEscape(signature)).append("\",")
		             .append("oauth_signature_method=\"").append(quoteEscape(OAUTH_SIGNATURE_METHOD)).append("\",")
		             .append("oauth_timestamp=\"").append(quoteEscape(timestamp)).append("\",")
		             .append("oauth_version=\"").append(quoteEscape(OAUTH_VERSION)).append("\"")
		;

		return stringBuilder.toString();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String buildSignature(String method, String url, String consumerKey, String consumerSecret, String nonce, String timestamp) throws Exception
	{
		String string = method.toUpperCase() + "&" + percentEncode(url) + "&" + percentEncode(
			/*---*/ percentEncode("oauth_consumer_key") + "=" + percentEncode(consumerKey)
			+ "&" + percentEncode("oauth_nonce") + "=" + percentEncode(nonce)
			+ "&" + percentEncode("oauth_signature_method") + "=" + percentEncode(OAUTH_SIGNATURE_METHOD)
			+ "&" + percentEncode("oauth_timestamp") + "=" + percentEncode(timestamp)
			+ "&" + percentEncode("oauth_version") + "=" + percentEncode(OAUTH_VERSION)
		);

		return percentEncode(doSign(string, consumerSecret + "&"));
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String quoteEscape(String text)
	{
		return text.replace("\"", "\\\"");
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String percentEncode(String text)
	{
		return URLEncoder.encode(text, StandardCharsets.UTF_8);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private String doSign(String toSign, String keyString) throws Exception
	{
		Mac mac = Mac.getInstance("HmacSHA1");

		mac.init(new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "HmacSHA1"));

		return Base64.getEncoder().encodeToString(mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8)));
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private String generateNonce(int size)
	{
		Random random = new Random();

		StringBuilder stringBuilder = new StringBuilder(size);

		for(int i = 0; i < size; i++)
		{
			stringBuilder.append(NONCE_BASE.charAt(random.nextInt(NONCE_BASE.length())));
		}

		return stringBuilder.toString();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private String generateTimestamp()
	{
		return Long.toString(System.currentTimeMillis() / 1000L);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
