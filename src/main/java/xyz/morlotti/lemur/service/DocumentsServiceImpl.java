package xyz.morlotti.lemur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.morlotti.lemur.clients.github.bean.GitHubTree;
import xyz.morlotti.lemur.clients.github.bean.GitHubTreeItem;
import xyz.morlotti.lemur.clients.github.service.GitHub;
import xyz.morlotti.lemur.service.bean.TreeItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DocumentsServiceImpl implements DocumentsService
{
	@Autowired
	public GitHub gitHub;

	public Map<String, TreeItem> dict = new HashMap<>();

	@Override
	public TreeItem getTree(String commitId)
	{
		int currentId = 1;

		/*------------------------------------------------------------------------------------------------------------*/

		GitHubTree tree = gitHub.fileTree(commitId);

		/*------------------------------------------------------------------------------------------------------------*/

		TreeItem rootFolder = new TreeItem(currentId, TreeItem.Type.FOLDER, "", "", 0, "/", "/");

		dict.put("/", rootFolder);

		currentId++;

		/*------------------------------------------------------------------------------------------------------------*/
		/* COMPUTE FOLDER TREE                                                                                        */
		/*------------------------------------------------------------------------------------------------------------*/

		for(GitHubTreeItem xx: tree.getTree())
		{
			if("tree".equals(xx.getType()))
			{
				TreeItem curFolder = rootFolder;

				List<String> currentPathList = new ArrayList<>();

				String[] subFolderNames = xx.getPath().split("/", -1);

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
						String currentPath = String.join("/", currentPathList);

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
			if("blob".equals(xx.getType()))
			{
				TreeItem curFolder = rootFolder;

				List<String> currentPathList = new ArrayList<>();

				String[] subFileNames = xx.getPath().split("/", -1);

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
						String currentPath = String.join("/", currentPathList);

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

		return rootFolder;
	}
}
