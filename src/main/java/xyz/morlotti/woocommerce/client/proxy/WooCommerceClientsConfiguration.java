package xyz.morlotti.woocommerce.client.proxy;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

public class WooCommerceClientsConfiguration
{
	/*----------------------------------------------------------------------------------------------------------------*/

	private static final String OAUTH_SIGNATURE_METHOD = "HMAC-SHA1";
	private static final String OAUTH_VERSION = "1.0";

	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${woocommerce.base_url}")
	private String baseUrl;

	@Value("${woocommerce.consumer_key}")
	private String consumerKey;

	@Value("${woocommerce.consumer_secret}")
	private String consumerSecret;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public RequestInterceptor requestInterceptor()
	{
		return requestTemplate -> {

			/*--------------------------------------------------------------------------------------------------------*/

			String nonce = "GdigHTLRTbL";

			String timestamp = String.valueOf(Instant.now().getEpochSecond());

			/*--------------------------------------------------------------------------------------------------------*/

			String signature;

			try
			{
				signature = buildSignature(requestTemplate.method(), "products", nonce, timestamp);
			}
			catch(Exception e)
			{
				signature = "";
			}

			/*--------------------------------------------------------------------------------------------------------*/

			requestTemplate.header("oauth_consumer_key", consumerKey);
			requestTemplate.header("oauth_nonce", nonce);
			requestTemplate.header("oauth_signature", signature);
			requestTemplate.header("aouth_signature_method", OAUTH_SIGNATURE_METHOD);
			requestTemplate.header("oauth_timestamp", timestamp);
			requestTemplate.header("oauth_version", OAUTH_VERSION);

			/*--------------------------------------------------------------------------------------------------------*/
		};
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String buildSignature(String method, String urlSuffix, String nonce, String timestamp) throws Exception
	{
		String string = method.toUpperCase() + "&" + percentEncode(baseUrl + urlSuffix) + "&" + percentEncode(
			percentEncode("oauth_consumer_key") + "=" + percentEncode(consumerKey)
				+ "&" + percentEncode("oauth_nonce") + "=" + percentEncode(nonce)
				+ "&" + percentEncode("oauth_signature_method") + "=" + percentEncode(OAUTH_SIGNATURE_METHOD)
				+ "&" + percentEncode("oauth_timestamp") + "=" + percentEncode(timestamp)
				+ "&" + percentEncode("oauth_version") + "=" + percentEncode(OAUTH_VERSION)
		);

		System.out.println(string);

		return percentEncode(doSign(string, consumerSecret + "&"));
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String percentEncode(String url)
	{
		return URLEncoder.encode(url, StandardCharsets.UTF_8);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private String doSign(String toSign, String keyString) throws Exception
	{
		SecretKeySpec key = new SecretKeySpec(keyString.getBytes(StandardCharsets.UTF_8), "HmacSHA1");

		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(key);

		byte[] bytes = mac.doFinal(toSign.getBytes(StandardCharsets.UTF_8));

		return Base64.getEncoder().encodeToString(bytes);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
