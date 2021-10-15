package xyz.morlotti.lemur.controller_api;

import java.io.File;
import java.nio.file.Files;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.DocumentsService;
import xyz.morlotti.lemur.clients.github.bean.GitHubContent;

@RestController
public class DocumentControllerAPI
{
	@Autowired
	DocumentsService documentsService;

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
}
