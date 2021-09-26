package xyz.morlotti.lemur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.morlotti.lemur.clients.github.proxy.GitHubClient;

@Service
public class DocumentsServiceImpl implements DocumentsService
{
	@Autowired
	private GitHubClient gitHubClient;
}
