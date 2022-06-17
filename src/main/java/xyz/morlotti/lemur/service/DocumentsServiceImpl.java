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
	// Création de la structure arborescence + liste à plat de tout les fichiers et dossiers à partir des infos de GitHub
	@Override
	public Map<String, TreeItem> getTree(String login, String repo, String branch)
	{
		int currentId = 1;

		// Map à plat de clefs 'le path complet du dossier ou fichier' et de valeurs 'le dossier ou le fichier de type TreeItem'

		Map<String, TreeItem> dict = new HashMap<>();

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubTree tree = gitHub.fileTree(login, repo, branch); // arbre à plat (= list simple d'objets GitHubTreeItem) de dossiers et fichier délivré par GitHub

		/*------------------------------------------------------------------------------------------------------------*/

		// Dossier racine de l'arbre final de dossiers et fichier

		TreeItem rootFolder = new TreeItem(currentId, TreeItem.Type.FOLDER, "", "", 0, "/", "/");

		dict.put("/", rootFolder);

		currentId++;

		/*------------------------------------------------------------------------------------------------------------*/
		/* COMPUTE FOLDER TREE                                                                                        */
		/*------------------------------------------------------------------------------------------------------------*/

		for(GitHubTreeItem xx: tree.getTree()) // On itère sur l'arbre à plat délivré par GitHub
		{
			if("tree".equals(xx.getType())) // On ne garde que les dossiers (tree = folder)
			{
				// Pour chaque dossier, on commence par initialiser le dossier courant au dossier racine

				TreeItem curFolder = rootFolder;

				// On découpe le path du dossier (au niveau des /) en sous éléments de paths

				String[] subFolderNames = xx.getPath().split("/", -1); // si la chaine coupée est vide : retourne un array vide (-1)

				// On créé une liste contenant tous les sous éléments de path jusqu'au niveau en cours dans l'itération qui suit

				List<String> currentPathList = new ArrayList<>();

				for(String subFolderName: subFolderNames) // par exemple [folder1, folder2, ..., folderN]
				{
					/*------------------------------------------------------------------------------------------------*/
					/* CONCATENATE SUB FOLDERS                                                                        */
					/*------------------------------------------------------------------------------------------------*/

					// on ajoute le sous éléments de path courant dans currentPathList

					currentPathList.add(subFolderName);

					/*------------------------------------------------------------------------------------------------*/
					/* CREATE THE FOLDER ENTRY IF NOT EXISTS                                                          */
					/*------------------------------------------------------------------------------------------------*/

					// Au sous éléments de path courant on regarde si l'élément courant existe dans l'arbre de dossier

					TreeItem newFolder = curFolder.getFolders().get(subFolderName);

					// S'il n'existe pas, il est créé

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

						// On ajoute le nouveau dossier dans le dossier courant

						curFolder.getFolders().put(subFolderName, newFolder);

						//  on enregistre le nouveau dossier dans la map à plat des paths complets et TreeItems

						dict.put(currentPath, newFolder);

						currentId++;
					}

					/*------------------------------------------------------------------------------------------------*/
					/* SET THE NEW FOLDER AS THE CURRENT FOLDER                                                       */
					/*------------------------------------------------------------------------------------------------*/

					// Le dossier en cours deviendra le dossier courant à l'itération suivante

					curFolder = newFolder;

					/*------------------------------------------------------------------------------------------------*/
				}
			}
		}

		/*------------------------------------------------------------------------------------------------------------*/
		/* COMPUTE FILE TREE                                                                                          */
		/*------------------------------------------------------------------------------------------------------------*/

		for(GitHubTreeItem xx: tree.getTree()) // On itère sur l'arbre à plat délivré par GitHub
		{
			if("blob".equals(xx.getType())) // On ne garde que les fichiers (blob = file)
			{
				// Pour chaque dossier, on commence par initialiser le dossier courant au dossier racine

				TreeItem curFolder = rootFolder;

				// On découpe le path du fichiers (au niveau des /) en sous éléments de paths

				String[] subFileNames = xx.getPath().split("/", -1); // si la chaine coupée est vide : retourne un array vide (-1)

				// On créé une liste contenant tous les sous éléments de path jusqu'au niveau en cours dans l'itération qui suit

				List<String> currentPathList = new ArrayList<>();

				for(String subFileName: subFileNames) // par exemple [folder1, folder2, ..., filename]
				{
					/*------------------------------------------------------------------------------------------------*/
					/* CONCATENATE SUB FILES                                                                          */
					/*------------------------------------------------------------------------------------------------*/

					// on ajoute le sous éléments de path courant dans currentPathList

					currentPathList.add(subFileName);

					/*------------------------------------------------------------------------------------------------*/
					/* CREATE THE FILE ENTRY IF NOT EXISTS                                                            */
					/*------------------------------------------------------------------------------------------------*/

					// Au sous éléments de path courant on regarde si l'élément courant existe dans l'arbre de dossier

					TreeItem newFolder = curFolder.getFolders().get(subFileName);

					// S'il n'existe pas, il est créé

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

						// On ajoute le nouveau fichier dans le dossier courant

						curFolder.getFiles().put(subFileName, newFile);

						//  on enregistre le nouveau fichier dans la map à plat des paths complets et TreeItems

						dict.put(currentPath, newFile);

						currentId++;
					}

					/*------------------------------------------------------------------------------------------------*/
					/* SET THE NEW FOLDER AS THE CURRENT FOLDER                                                       */
					/*------------------------------------------------------------------------------------------------*/

					// Le dossier en cours deviendra le dossier courant à l'itération suivante

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

	private long updateSizes(TreeItem item) // méthode récursive pour calculer les tailles des dossiers à partir de la taille de tous les fichiers fils
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
