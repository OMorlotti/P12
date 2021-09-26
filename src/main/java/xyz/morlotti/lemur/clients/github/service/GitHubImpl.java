package xyz.morlotti.lemur.clients.github.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubUser;
import xyz.morlotti.lemur.clients.github.bean.GitHubVersion;
import xyz.morlotti.lemur.clients.github.proxy.GitHubClient;

@Service
public class GitHubImpl implements GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private GitHubClient gitHubClients;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubUser me()
	{
		return gitHubClients.me();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubTree fileTree(String commitId)
	{
		return gitHubClients.fileTree(commitId, true);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<GitHubVersion> versions(String path)
	{
		return gitHubClients.versions(path);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
