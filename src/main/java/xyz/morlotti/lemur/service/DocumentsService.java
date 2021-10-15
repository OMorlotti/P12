package xyz.morlotti.lemur.service;

import java.util.Map;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;

public interface DocumentsService
{
	Map<String, TreeItem> getTree(String commitId);

	GitHubContent getContent(String path);
}
