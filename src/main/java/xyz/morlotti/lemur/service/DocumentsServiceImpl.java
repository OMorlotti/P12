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
import xyz.morlotti.lemur.clients.github.bean.GitHubTreeItem;

@Service
public class DocumentsServiceImpl implements DocumentsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private GitHub gitHub;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Map<String, TreeItem> getTree(String commitId)
	{
		int currentId = 1;

		Map<String, TreeItem> dict = new HashMap<>();

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

	private long updateSizes(TreeItem item)
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
