package xyz.morlotti.lemur.service;

import java.util.Map;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;

public interface DocumentsService
{
	Map<String, TreeItem> getTree(String login, String repo, String branch);

	GitHubContent getContent(String login, String repo, String path);

	void addFolder(String login, String repo, String branch, String path, String name);

	void addFile(String login, String repo, String branch, String path, String name, byte[] content);

	void updateFile(String login, String repo, String branch, String path, String name, String hash, byte[] content);

	void renameFile(String login, String repo, String branch, String path, String oldName, String newName, String hash);

	void deleteFile(String login, String repo, String branch, String path, String name, String hash);
}
