package xyz.morlotti.lemur.controllers;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.service.TagsService;

@Controller
public class TagsController
{
	@Autowired
	TagsService tagsService;

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String tags()
	{
		return "tags";
	}

	@RequestMapping(value = "/tags", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addUpdateTag(@ModelAttribute("tag") Tag tag, Model model)
	{
		try
		{
			if(tag.getId() < 0)
			{
				// Add
				tagsService.addTag(tag);
			}
			else
			{
				// Update
				tagsService.updateTag(tag);
			}
		}
		catch(RuntimeException e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "tags";
	}
}
