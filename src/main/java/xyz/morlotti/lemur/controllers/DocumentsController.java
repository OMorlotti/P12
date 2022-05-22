package xyz.morlotti.lemur.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.service.DocumentsService;

import java.util.Map;

@Controller
public class DocumentsController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${github.commit_id:master}")
	String gitCommitId;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	DocumentsService documentsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String documents(@RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		try
		{
			Map<String, TreeItem> map = documentsService.getTree(gitCommitId);

			if(map.containsKey(path))
			{
				TreeItem treeItem = map.get(path);

				model.addAttribute("treeItem", treeItem);

				model.addAttribute("path", path);

				return "documents";
			}
			else
			{
				model.addAttribute("path", path);

				return "notFound";
			}
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());

			return "error";
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents/{commitId}", method = RequestMethod.GET)
	public String documents(@PathVariable("commitId") String commitId, @RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		Map<String, TreeItem> map = documentsService.getTree(commitId);

		if(map.containsKey(path))
		{
			TreeItem treeItem = map.get(path);

			model.addAttribute("treeItem", treeItem);

			model.addAttribute("path", path);

			return "documents";
		}
		else
		{
			model.addAttribute("path", path);

			return "notFound";
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
