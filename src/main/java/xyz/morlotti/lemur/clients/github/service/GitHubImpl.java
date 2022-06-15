package xyz.morlotti.lemur.clients.github.service;

import java.util.List;
import java.math.BigInteger;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.clients.github.bean.*;
import xyz.morlotti.lemur.clients.github.proxy.GitHubClient;

@Service
public class GitHubImpl implements GitHub
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private GitHubClient gitHubClients;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubUser me(String login)
	{
		return gitHubClients.me(login);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubTree fileTree(String login, String repo, String branch)
	{
		return gitHubClients.fileTree(login, repo, branch, true);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public List<GitHubVersion> versions(String login, String repo, String path)
	{
		return gitHubClients.versions(login, repo, path);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubContent getContent(String login, String repo, String path)
	{
		return gitHubClients.getContent(login, repo, path);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFolder(String login, String repo, String branch, String path, String name)
	{
		// Sous git, on ne peut pas créer un dossier vide ! Pour le faire, on ajoute simplement au repo un fichier ".empty" dans le path que l'on souhaite créer

		// De même, pour supprimer un dossier, il faut supprimer tous les fichiers et dossiers qu'il contient

		if("/".equals(path))
		{
			addFile(login, repo, branch,"/" + name, ".empty", new byte[] {}); // quand on est dans le dossier root, le path est "/"
		}
		else
		{
			addFile(login, repo, branch,path + "/" + name, ".empty", new byte[] {}); // quand on est pas dans le dossier root, le path est "path"
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFile(String login, String repo, String branch, String path, String name, byte[] content)
	{
		updateFile(login, repo, branch, path, name, "", content);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void updateFile(String login, String repo, String branch, String path, String name, String sha, byte[] content)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		while(path.startsWith("/")) // GitHub demande re retirer dans les paths tous les "/" au debut
		{
			path = path.substring(1);
		}

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubUpdate update = new GitHubUpdate();

		try
		{
			update.setOwner("bot");

			update.setMessage("updating `" + path + "/" + name + "`");

			update.setDecodedContent(content);

			update.setSha(sha);

			update.setBranch(branch);

			gitHubClients.updateFile(login, repo, path + "/" + name, update);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void renameFile(String login, String repo, String branch, String path, String oldName, String newName, String hash)
	{
		GitHubContent content = gitHubClients.getContent(login, repo, path + '/' + oldName);

		if(content != null)
		{
			try
			{
				addFile(login, repo, branch, path, newName, content.getDecodedContent());

				deleteFile(login, repo, branch, path, oldName, hash);
			}
			catch(Exception e)
			{
				System.out.println(e.getMessage());
			}
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void deleteFile(String login, String repo, String branch, String path, String name, String hash)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		while(path.startsWith("/")) // GitHub demande re retirer dans les paths tous les "/" au debut
		{
			path = path.substring(1);
		}

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubDelete delete = new GitHubDelete();

		delete.setOwner("bot");

		delete.setRepo(repo);

		delete.setPath(path + "/" + name);

		delete.setMessage("deleting `" + path + "/" + name + "`");

		delete.setSha(hash);

		gitHubClients.deleteFile(login, repo, path + "/" + name, delete);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static String toHex(byte[] bytes) // Pour afficher un hash en hexadécimal (chiffres allant de 0 (pour 0) à F pour 15))
	{
		BigInteger bi = new BigInteger(1, bytes);

		return String.format("%0" + (bytes.length << 1) + "X", bi);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
