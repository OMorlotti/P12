package xyz.morlotti.woocommerce.client.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.woocommerce.client.bean.GitHubTree;
import xyz.morlotti.woocommerce.client.bean.GitHubUser;
import xyz.morlotti.woocommerce.client.proxy.GitHubClient;

@Service
public class GitHubImpl implements GitHub
{
	@Autowired
	private GitHubClient gitHubClients;

	@Override
	public GitHubUser me()
	{
		return gitHubClients.me();
	}

	@Override
	public GitHubTree tree(String commitId)
	{
		return gitHubClients.tree(commitId, true);
	}
}
