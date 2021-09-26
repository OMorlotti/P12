package xyz.morlotti.lemur.clients.github.service;

import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubUser;
import xyz.morlotti.lemur.clients.github.bean.GitHubVersion;

import java.util.List;

public interface GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubUser me();

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubTree fileTree(String commitId);

	/*----------------------------------------------------------------------------------------------------------------*/

	List<GitHubVersion> versions(String path);

	/*----------------------------------------------------------------------------------------------------------------*/
}
