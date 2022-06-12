package xyz.morlotti.lemur.controllers_html;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import xyz.morlotti.lemur.service.ConfigService;
import xyz.morlotti.lemur.service.bean.TreeItem;
import xyz.morlotti.lemur.service.DocumentsService;

import java.util.Map;

@Controller
public class DocumentsController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	DocumentsService documentsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String documents(@RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		Map<String, String> config = configService.getConfig();

		String login = config.getOrDefault("github_username", "");
		String repo = config.getOrDefault("github_repo", "");
		String branch = config.getOrDefault("github_branch", "");

		try
		{
			Map<String, TreeItem> map = documentsService.getTree(login, repo, branch);

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

	@RequestMapping(value = "/documents/{branch}", method = RequestMethod.GET)
	public String documents(@PathVariable("branch") String branch, @RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		Map<String, String> config = configService.getConfig();

		String login = config.getOrDefault("github_username", "");
		String repo = config.getOrDefault("github_repo", "");

		Map<String, TreeItem> map = documentsService.getTree(login, repo, branch);

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
