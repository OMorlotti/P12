package xyz.morlotti.woocommerce.client.service;

import xyz.morlotti.woocommerce.client.bean.GitHubTree;
import xyz.morlotti.woocommerce.client.bean.GitHubUser;
import xyz.morlotti.woocommerce.client.bean.GitHubVersion;

import java.util.List;

public interface GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubUser me();

	/*----------------------------------------------------------------------------------------------------------------*/

	GitHubTree tree(String commitId);

	/*----------------------------------------------------------------------------------------------------------------*/

	List<GitHubVersion> versions(String path);

	/*----------------------------------------------------------------------------------------------------------------*/
}
