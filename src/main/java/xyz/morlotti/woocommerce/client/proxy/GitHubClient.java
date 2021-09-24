package xyz.morlotti.woocommerce.client.proxy;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.morlotti.woocommerce.client.bean.GitHubTree;
import xyz.morlotti.woocommerce.client.bean.GitHubUser;
import xyz.morlotti.woocommerce.client.bean.GitHubVersion;

@FeignClient(value = "github", url = "${github.base_url}", configuration = GitHubClientConfiguration.class)
public interface GitHubClient
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/users/${github.login}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubUser me();

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/${github.login}/${github.repo}/git/trees/{commitId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubTree tree(
		@PathVariable(value = "commitId") String commitId,
		@RequestParam(value = "recursive", required = false, defaultValue = "true") boolean recursive
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/${github.login}/${github.repo}/commits?path={path}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<GitHubVersion> versions(
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/
}
