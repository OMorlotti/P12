package xyz.morlotti.lemur.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.morlotti.lemur.service.DocumentsService;

@Controller
public class DocumentsController
{
	@Autowired
	DocumentsService documentsService;

	@RequestMapping(value = "/documents", method = RequestMethod.GET)
	public String documents()
	{
		return "documents";
	}
}
