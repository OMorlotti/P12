package xyz.morlotti.lemur.controllers_api;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.controllers_api.bean.NewFile;
import xyz.morlotti.lemur.controllers_api.bean.NewFolder;
import xyz.morlotti.lemur.service.DocumentsService;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;
import xyz.morlotti.lemur.service.bean.TreeItem;

@RestController
public class DocumentControllerAPI
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${github.commit_id:master}")
	String gitCommitId;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	DocumentsService documentsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents/download", method = RequestMethod.GET)
	public void documents(@RequestParam(name = "path", required = true) String path, HttpServletResponse response)
	{
		try
		{
			/*--------------------------------------------------------------------------------------------------------*/

			GitHubContent gitHubContent = documentsService.getContent(path);

			byte[] content = gitHubContent.getDecodedContent();

			/*--------------------------------------------------------------------------------------------------------*/

			response.setContentType(Files.probeContentType(new File(gitHubContent.getName()).toPath()));

			response.setHeader("Content-Disposition", "attachment; filename=" + gitHubContent.getName());
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");

			/*--------------------------------------------------------------------------------------------------------*/

			response.getOutputStream().write(content);

			/*--------------------------------------------------------------------------------------------------------*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents/folder", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> newFolder(@RequestBody NewFolder newFolder, HttpServletResponse response)
	{
		Map<String, TreeItem> map = documentsService.getTree(gitCommitId);

		if(map.containsKey(newFolder.getPath()))
		{
			TreeItem treeItem = map.get(newFolder.getPath());

			if(!treeItem.getFolders().containsKey(newFolder.getName())
			   &&
			   !treeItem.getFiles().containsKey(newFolder.getName())
			) {
				documentsService.addFolder(newFolder.getPath(), newFolder.getName());

				return ResponseEntity.status(HttpStatus.OK).body("folder `" + newFolder.getPath() + "/" + newFolder.getName() + "` added with success");
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("folder `" + newFolder.getPath() + "/" + newFolder.getName() + "` already exists");
			}
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("folder `" + newFolder.getPath() + "` not found");
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents/file", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> newFile(@RequestBody NewFile newFile, HttpServletResponse response)
	{
		Map<String, TreeItem> map = documentsService.getTree(gitCommitId);

		if(map.containsKey(newFile.getPath()))
		{
			TreeItem treeItem = map.get(newFile.getPath());

			if(!treeItem.getFolders().containsKey(newFile.getName()))
			{
				if(!treeItem.getFiles().containsKey(newFile.getName()))
				{
					documentsService.addFile(newFile.getPath(), newFile.getName(), newFile.getContent());
				}
				else
				{
					documentsService.updateFile(newFile.getPath(), newFile.getName(), treeItem.getFiles().get(newFile.getName()).getHash(), newFile.getContent());
				}

				return ResponseEntity.status(HttpStatus.OK).body("folder `" + newFile.getPath() + "/" + newFile.getName() + "` added with success");
			}
			else
			{
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("folder `" + newFile.getPath() + "/" + newFile.getName() + "` already exists");
			}
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("folder `" + newFile.getPath() + "` not found");
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
