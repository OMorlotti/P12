package xyz.morlotti.woocommerce.client.proxy;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;

import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class GitHubClientConfiguration
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${github.login}")
	private String login;

	@Value("${github.token}")
	private String token;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public RequestInterceptor requestInterceptor()
	{
		return requestTemplate -> {

			String data = login + ":" + token;

			requestTemplate.header("Authorization", "Basic " + Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8)));
		};
	};

	/*----------------------------------------------------------------------------------------------------------------*/
}
