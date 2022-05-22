package xyz.morlotti.lemur.clients.github.service;

import java.util.List;

import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubUser;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;
import xyz.morlotti.lemur.clients.github.bean.GitHubVersion;

public interface GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubUser me();

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubTree fileTree(String commitId);

	/*----------------------------------------------------------------------------------------------------------------*/

	List<GitHubVersion> versions(String path);

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubContent getContent(String path);

	/*----------------------------------------------------------------------------------------------------------------*/

	void addFolder(String path, String name);

	/*----------------------------------------------------------------------------------------------------------------*/

	void addFile(String path, String name, byte[] content);

	/*----------------------------------------------------------------------------------------------------------------*/

	void updateFile(String path, String name, String sha, byte[] content);

	/*----------------------------------------------------------------------------------------------------------------*/
}
