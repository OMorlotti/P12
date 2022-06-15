package xyz.morlotti.lemur.clients.github.service;

import java.util.List;

import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubUser;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;
import xyz.morlotti.lemur.clients.github.bean.GitHubVersion;

public interface GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubUser me(String login); // jamais utilisé dans le projet

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubTree fileTree(String login, String repo, String branch);

	/*----------------------------------------------------------------------------------------------------------------*/

	List<GitHubVersion> versions(String login, String repo, String path);  // jamais utilisé dans le projet

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubContent getContent(String login, String repo, String path);

	/*----------------------------------------------------------------------------------------------------------------*/

	void addFolder(String login, String repo, String branch, String path, String name);

	/*----------------------------------------------------------------------------------------------------------------*/

	void addFile(String login, String repo, String branch, String path, String name, byte[] content);

	/*----------------------------------------------------------------------------------------------------------------*/

	void updateFile(String login, String repo, String branch, String path, String name, String sha, byte[] content);

	/*----------------------------------------------------------------------------------------------------------------*/

	void renameFile(String login, String repo, String branch, String path, String oldName, String newName, String hash);

	/*----------------------------------------------------------------------------------------------------------------*/

	void deleteFile(String login, String repo, String branch, String path, String name, String hash);

	/*----------------------------------------------------------------------------------------------------------------*/
}
