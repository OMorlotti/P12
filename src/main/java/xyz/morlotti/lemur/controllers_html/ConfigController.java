package xyz.morlotti.lemur.controllers_html;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.morlotti.lemur.service.ConfigService;

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
	public String addUpdateConfig(@RequestBody MultiValueMap<String, String> formData, Model model)
	{
		// Construit une map de clé/valeur de config à partir de l'objet MultiValueMap "formData"
		Map<String, String> config = formData.entrySet().stream().filter(x -> x.getValue().size() > 0)
		                                                         .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue().get(0)))
		;

		try
		{
			configService.setConfig(config);
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", e.getMessage());
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
