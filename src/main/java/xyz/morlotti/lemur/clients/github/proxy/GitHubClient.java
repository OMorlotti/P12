package xyz.morlotti.lemur.clients.github.proxy;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import xyz.morlotti.lemur.clients.github.bean.*;

@FeignClient(value = "github", url = "${github.base_url}", configuration = GitHubClientConfiguration.class)
public interface GitHubClient
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/users/${github.login}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubUser me();

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/${github.login}/${github.repo}/git/trees/{commitId}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubTree fileTree(
		@PathVariable(value = "commitId") String commitId,
		@RequestParam(value = "recursive", required = false, defaultValue = "true") boolean recursive
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/${github.login}/${github.repo}/commits?path={path}", produces = MediaType.APPLICATION_JSON_VALUE)
	List<GitHubVersion> versions(
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.GET, value = "/repos/${github.login}/${github.repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	GitHubContent getContent(
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.DELETE, value = "/repos/${github.login}/${github.repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	void renameFile(
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.DELETE, value = "/repos/${github.login}/${github.repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	void deleteFile(
		@PathVariable(value = "path") String path
	);

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(method = RequestMethod.PUT, value = "/repos/${github.login}/${github.repo}/contents/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	void updateFile(
		@PathVariable(value = "path") String path,
		@RequestBody GitHubUpdate update
	);

	/*----------------------------------------------------------------------------------------------------------------*/
}
