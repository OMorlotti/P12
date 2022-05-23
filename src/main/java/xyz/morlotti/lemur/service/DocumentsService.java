package xyz.morlotti.lemur.service;

import java.util.Map;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;

public interface DocumentsService
{
	Map<String, TreeItem> getTree(String commitId);

	GitHubContent getContent(String path);

	void addFolder(String path, String name);

	void addFile(String path, String name, byte[] content);

	void updateFile(String path, String name, String hash, byte[] content);

	void renameFile(String path, String oldName, String newName, String hash);

	void deleteFile(String path, String name, String hash);
}
