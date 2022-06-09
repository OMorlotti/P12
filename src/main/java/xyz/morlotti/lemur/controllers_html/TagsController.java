package xyz.morlotti.lemur.controllers_html;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.model.bean.Tag;
import xyz.morlotti.lemur.service.TagsService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class TagsController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	TagsService tagsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public String tags()
	{
		return "tags";
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/tags", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addUpdateTag(@Valid @ModelAttribute("tag") Tag tag, BindingResult result, Model model) // BindingResult = test erreur remplissage du bean
	{
		if(!result.hasErrors())
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
			catch(Exception e)
			{
				model.addAttribute("errorMessage", e.getMessage());
			}
		}
		else
		{
			model.addAttribute("errorMessage", result.getAllErrors().stream().map(x ->
				String.format("%s: %s",
					((FieldError) x).    getField     (),
					((FieldError) x).getDefaultMessage()
				)
			).collect(Collectors.joining(", ")));
		}

		return "tags";
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
