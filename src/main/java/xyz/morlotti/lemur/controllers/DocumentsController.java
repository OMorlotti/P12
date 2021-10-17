package xyz.morlotti.lemur.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.service.DocumentsService;

import java.util.Map;

@Controller
public class DocumentsController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	DocumentsService documentsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String documents(@RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		try
		{
			Map<String, TreeItem> map = documentsService.getTree("master");

			if(map.containsKey(path))
			{
				TreeItem treeItem = map.get(path);

				model.addAttribute("treeItem", treeItem);

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
