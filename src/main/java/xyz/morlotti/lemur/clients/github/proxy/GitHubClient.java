package xyz.morlotti.lemur.clients.github.proxy;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.morlotti.lemur.clients.github.bean.*;

@FeignClient(value = "github", url = "${github.base_url}", configuration = GitHubClientConfiguration.class)
public interface GitHubClient
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/users/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubUser me(
		@PathVariable(value = "login") String login
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/{login}/{repo}/git/trees/{branch}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubTree fileTree(
		@PathVariable(value = "login") String login,
		@PathVariable(value = "repo") String repo,
		@PathVariable(value = "branch") String branch,
		@RequestParam(value = "recursive", required = false, defaultValue = "true") boolean recursive
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/{login}/{repo}/commits?path={path}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<GitHubVersion> versions(
		@PathVariable(value = "login") String login,
		@PathVariable(value = "repo") String repo,
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/{login}/{repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubContent getContent(
		@PathVariable(value = "login") String login,
		@PathVariable(value = "repo") String repo,
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.PUT, value = "/repos/{login}/{repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	void updateFile(
		@PathVariable(value = "login") String login,
		@PathVariable(value = "repo") String repo,
		@PathVariable(value = "path") String path,
		@RequestBody GitHubUpdate update
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.DELETE, value = "/repos/{login}/{repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	void deleteFile(
		@PathVariable(value = "login") String login,
		@PathVariable(value = "repo") String repo,
		@PathVariable(value = "path") String path,
		@RequestBody GitHubDelete delete
	);

	/*----------------------------------------------------------------------------------------------------------------*/
}
