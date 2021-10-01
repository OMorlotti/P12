package xyz.morlotti.lemur.service;

import xyz.morlotti.lemur.service.bean.TreeItem;

import java.util.Map;

public interface DocumentsService
{
	Map<String, TreeItem> getTree(String commitId);
}
