package xyz.morlotti.lemur.controllers_html;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import xyz.morlotti.lemur.service.ConfigService;

import javax.validation.Valid;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ConfigController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/config", method = RequestMethod.GET)
	public String config(Model model)
	{
		try
		{
			model.addAttribute("config", configService.getConfig());
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "config";
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/config", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String addUpdateConfig(@Valid @ModelAttribute("config") Map<String, String> config, BindingResult result, Model model) // BindingResult = test erreur remplissage du bean
	{
		if(!result.hasErrors())
		{
			try
			{
				configService.setConfig(config);
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

		try
		{
			model.addAttribute("config", configService.getConfig());
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
		}

		return "config";
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
