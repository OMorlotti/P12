package xyz.morlotti.lemur.clients.github.proxy;

import java.util.Map;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import feign.RequestInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ConfigService;

public class GitHubClientConfiguration
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public RequestInterceptor requestInterceptor()
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Map<String, String> config = configService.getConfig();

		String username = config.getOrDefault("github_username", "");
		String token    = config.getOrDefault("github_token"   , "");

		/*------------------------------------------------------------------------------------------------------------*/

		return requestTemplate -> {

			String data = username + ":" + token;

			String encoded = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));

			requestTemplate.header("Authorization", "Basic " + encoded);
		};

		/*------------------------------------------------------------------------------------------------------------*/
	};

	/*----------------------------------------------------------------------------------------------------------------*/
}
