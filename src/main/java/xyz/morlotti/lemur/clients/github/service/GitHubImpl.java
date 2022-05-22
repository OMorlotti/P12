package xyz.morlotti.lemur.clients.github.service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.github.bean.*;
import xyz.morlotti.lemur.clients.github.proxy.GitHubClient;

@Service
public class GitHubImpl implements GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${github.commit_id:master}")
	String gitCommitId;

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

	@Override
	public GitHubContent getContent(String path)
	{
		return gitHubClients.getContent(path);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFolder(String path, String name)
	{
		if("/".equals(path))
		{
			addFile("/" + name, ".empty", new byte[] {});
		}
		else
		{
			addFile(path + "/" + name, ".empty", new byte[] {});
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFile(String path, String name, byte[] content)
	{
		updateFile(path, name, "", content);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void updateFile(String path, String name, String sha, byte[] content)
	{
		System.out.println("-> " + path + "/" + name + ", sha:" + sha);

		/*------------------------------------------------------------------------------------------------------------*/

		while(path.startsWith("/"))
		{
			path = path.substring(1);
		}

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubUpdate update = new GitHubUpdate();

		try
		{
			update.setMessage("updating `" + path + "/" + name + "`");

			update.setDecodedContent(content);

			update.setSha(sha);

			update.setBranch(gitCommitId);

			gitHubClients.updateFile(path + "/" + name, update);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static String toHex(byte[] bytes)
	{
		BigInteger bi = new BigInteger(1, bytes);

		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
