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

@Controller
public class DocumentsController
{
	@Autowired
	DocumentsService documentsService;

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String documents(@RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		TreeItem treeItem = documentsService.getTree("master").get(path);

		System.out.println(treeItem);

		model.addAttribute("treeItem", treeItem);

		return "documents";
	}

	@RequestMapping(value = "/documents/{commitId}", method = RequestMethod.GET)
	public String documents(@PathVariable("commitId") String commitId, @RequestParam(name = "path", defaultValue = "/", required = false) String path, Model model)
	{
		TreeItem treeItem = documentsService.getTree(commitId).get(path);

		System.out.println(treeItem);

		model.addAttribute("treeItem", treeItem);

		return "documents";
	}
}
