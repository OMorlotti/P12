package xyz.morlotti.lemur.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.clients.github.service.GitHub;
import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;
import xyz.morlotti.lemur.clients.github.bean.GitHubTreeItem;

@Service
public class DocumentsServiceImpl implements DocumentsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private GitHub gitHub;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Map<String, TreeItem> getTree(String login, String repo, String branch)
	{
		int currentId = 1;

		Map<String, TreeItem> dict = new HashMap<>();

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubTree tree = gitHub.fileTree(login, repo, branch);

		/*------------------------------------------------------------------------------------------------------------*/

		TreeItem rootFolder = new TreeItem(currentId, TreeItem.Type.FOLDER, "", "", 0, "/", "/");

		dict.put("/", rootFolder);

		currentId++;

		/*------------------------------------------------------------------------------------------------------------*/
		/* COMPUTE FOLDER TREE                                                                                        */
		/*------------------------------------------------------------------------------------------------------------*/

		for(GitHubTreeItem xx: tree.getTree())
		{
			if("tree".equals(xx.getType())) // tree = folder
			{
				TreeItem curFolder = rootFolder;

				List<String> currentPathList = new ArrayList<>();

				String[] subFolderNames = xx.getPath().split("/", -1); // si la chaine coupée est vide : retourne un array vide (-1)

				for(String subFolderName: subFolderNames)
				{
					/*------------------------------------------------------------------------------------------------*/
					/* CONCATENATE SUB FOLDERS                                                                        */
					/*------------------------------------------------------------------------------------------------*/

					currentPathList.add(subFolderName);

					/*------------------------------------------------------------------------------------------------*/
					/* CREATE THE FOLDER ENTRY IF NOT EXISTS                                                          */
					/*------------------------------------------------------------------------------------------------*/

					TreeItem newFolder = curFolder.getFolders().get(subFolderName);

					if(newFolder == null)
					{
						String currentPath = "/" + String.join("/", currentPathList);

						newFolder = new TreeItem(
							currentId,
							TreeItem.Type.FOLDER,
							"",
							"",
							0,
							currentPath,
							subFolderName
						);

						curFolder.getFolders().put(subFolderName, newFolder);

						dict.put(currentPath, newFolder);

						currentId++;
					}

					/*------------------------------------------------------------------------------------------------*/
					/* SET THE NEW FOLDER AS THE CURRENT FOLDER                                                       */
					/*------------------------------------------------------------------------------------------------*/

					curFolder = newFolder;

					/*------------------------------------------------------------------------------------------------*/
				}
			}
		}

		/*------------------------------------------------------------------------------------------------------------*/
		/* COMPUTE FILE TREE                                                                                          */
		/*------------------------------------------------------------------------------------------------------------*/

		for(GitHubTreeItem xx: tree.getTree())
		{
			if("blob".equals(xx.getType())) // blob = file
			{
				TreeItem curFolder = rootFolder;

				List<String> currentPathList = new ArrayList<>();

				String[] subFileNames = xx.getPath().split("/", -1); // si la chaine coupée est vide : retourne un array vide (-1)

				for(String subFileName: subFileNames)
				{
					/*------------------------------------------------------------------------------------------------*/
					/* CONCATENATE SUB FILES                                                                          */
					/*------------------------------------------------------------------------------------------------*/

					currentPathList.add(subFileName);

					/*------------------------------------------------------------------------------------------------*/
					/* CREATE THE FILE ENTRY IF NOT EXISTS                                                            */
					/*------------------------------------------------------------------------------------------------*/

					TreeItem newFolder = curFolder.getFolders().get(subFileName);

					if(newFolder == null)
					{
						String currentPath = "/" + String.join("/", currentPathList);

						TreeItem newFile = new TreeItem(
							currentId,
							TreeItem.Type.FILE,
							xx.getSha(),
							xx.getSha().substring(0, 7),
							xx.getSize(),
							currentPath,
							subFileName
						);

						curFolder.getFiles().put(subFileName, newFile);

						dict.put(currentPath, newFile);

						currentId++;
					}

					/*------------------------------------------------------------------------------------------------*/
					/* SET THE NEW FOLDER AS THE CURRENT FOLDER                                                       */
					/*------------------------------------------------------------------------------------------------*/

					curFolder = newFolder;

					/*------------------------------------------------------------------------------------------------*/
				}
			}
		}

		/*------------------------------------------------------------------------------------------------------------*/
		/* UPDATE FOLDER SIZES                                                                                        */
		/*------------------------------------------------------------------------------------------------------------*/

		updateSizes(rootFolder);

		/*------------------------------------------------------------------------------------------------------------*/

		return dict;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public GitHubContent getContent(String login, String repo, String path)
	{
		return gitHub.getContent(login, repo, path);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFolder(String login, String repo, String branch, String path, String name)
	{
		gitHub.addFolder(login, repo, branch, path, name);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void addFile(String login, String repo, String branch, String path, String name, byte[] content)
	{
		gitHub.addFile(login, repo, branch, path, name, content);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void updateFile(String login, String repo, String branch, String path, String name, String hash, byte[] content)
	{
		gitHub.updateFile(login, repo, branch, path, name, hash, content);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void renameFile(String login, String repo, String branch, String path, String oldName, String newName, String hash)
	{
		gitHub.renameFile(login, repo, branch, path, oldName, newName, hash);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void deleteFile(String login, String repo, String branch, String path, String name, String hash)
	{
		gitHub.deleteFile(login, repo, branch, path, name, hash);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	private long updateSizes(TreeItem item) // méthode récursive pour calculer les tailles des dossiers à partir de la taille de tous les éléments contenus
	{
		long result = 0;

		for(TreeItem folder: item.getFolders().values())
		{
			result += updateSizes(folder);
		}

		for(TreeItem file: item.getFiles().values())
		{
			result += file.getSize();
		}

		item.setSize(result);

		return result;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
