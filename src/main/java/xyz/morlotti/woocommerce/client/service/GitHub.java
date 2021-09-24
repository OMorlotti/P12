package xyz.morlotti.woocommerce.client.service;

import xyz.morlotti.woocommerce.client.bean.GitHubTree;
import xyz.morlotti.woocommerce.client.bean.GitHubUser;

public interface GitHub
{
	GitHubUser me();

	GitHubTree tree(String commitId);
}
