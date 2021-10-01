package xyz.morlotti.lemur.service;

import xyz.morlotti.lemur.service.bean.TreeItem;

public interface DocumentsService
{
	TreeItem getTree(String commitId);
}
