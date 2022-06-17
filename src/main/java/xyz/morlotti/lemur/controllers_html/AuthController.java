package xyz.morlotti.lemur.controllers_html;

import java.util.Map;
import java.util.Calendar;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import xyz.morlotti.lemur.EmailSender;
import xyz.morlotti.lemur.service.ConfigService;
import xyz.morlotti.lemur.security.jwt.JwtUtils;
import xyz.morlotti.lemur.controllers_html.bean.Credentials;

@Controller
public class AuthController
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private JwtUtils jwtUtils;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private AuthenticationManager authenticationManager;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private EmailSender emailSender;

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/remind", method = RequestMethod.GET)
	public ModelAndView remind(ModelMap model)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Map<String, String> config = configService.getConfig();

		/*------------------------------------------------------------------------------------------------------------*/

		try
		{
			if(config.containsKey("email"))
			{
				String email = config.get("email");

				emailSender.sendMessage(email, email, "", "Mot de passe \"Le Mur Grenoble\"", "Bonjour,\n\nVotre mot de passe \"Le Mur\" est: " + config.getOrDefault("password", "N/A") + "\n\nBien cordialement\nLe Mur Grenoble");

				model.addAttribute("successMessage", "Un email vient d'être envoyé");
			}
			else
			{
				model.addAttribute("errorMessage", "Aucun e-mail n'a été configuré");
			}
		}
		catch(Exception e)
		{
			model.addAttribute("errorMessage", "Erreur d'envoi du message: " + e.getMessage());
		}

		/*------------------------------------------------------------------------------------------------------------*/

		return signIn1(model);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET) //formulaire
	public ModelAndView signIn1(ModelMap model)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		model.addAttribute("year", year);

		model.addAttribute("hide", true);

		/*------------------------------------------------------------------------------------------------------------*/

		return new ModelAndView("sign-in");

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView signIn2(@ModelAttribute("credentials") Credentials credentials, ModelMap model, HttpServletResponse httpServletResponse)
	{
		try
		{
			/*--------------------------------------------------------------------------------------------------------*/

			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
			);

			SecurityContextHolder.getContext().setAuthentication(authentication);

			/*--------------------------------------------------------------------------------------------------------*/

			String token = jwtUtils.generateJwtToken(credentials.getUsername());

			JwtUtils.createTokenCookie(httpServletResponse, token);

			/*--------------------------------------------------------------------------------------------------------*/

			return new ModelAndView("redirect:/");

			/*--------------------------------------------------------------------------------------------------------*/
		}
		catch(Exception e)
		{
			/*--------------------------------------------------------------------------------------------------------*/

			model.addAttribute("errorMessage", "Login et/ou mot de passe incorrect");

			/*--------------------------------------------------------------------------------------------------------*/

			return signIn1(model);

			/*--------------------------------------------------------------------------------------------------------*/
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/sign-out", method = RequestMethod.GET)
	public ModelAndView signOut(HttpServletResponse httpServletResponse)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		JwtUtils.deleteTokenCookie(httpServletResponse);

		/*------------------------------------------------------------------------------------------------------------*/

		return new ModelAndView("redirect:/sign-in");

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
