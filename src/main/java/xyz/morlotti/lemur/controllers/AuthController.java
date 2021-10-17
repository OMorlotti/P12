package xyz.morlotti.lemur.controllers;

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

import xyz.morlotti.lemur.controllers.bean.Credentials;
import xyz.morlotti.lemur.security.jwt.JwtUtils;


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

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public String signIn1(ModelMap model)
	{
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		model.addAttribute("year", year);

		return "sign-in";
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

			Calendar cal = Calendar.getInstance();

			int year = cal.get(Calendar.YEAR);

			model.addAttribute("year", year);

			model.addAttribute("errorMessage", e.getMessage());

			/*--------------------------------------------------------------------------------------------------------*/

			return new ModelAndView("sign-in", model);

			/*--------------------------------------------------------------------------------------------------------*/
		}
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@RequestMapping(value = "/sign-out", method = RequestMethod.GET)
	public String signOut(ModelMap model, HttpServletResponse httpServletResponse)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		JwtUtils.deleteTokenCookie(httpServletResponse);

		/*------------------------------------------------------------------------------------------------------------*/

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		model.addAttribute("year", year);

		/*------------------------------------------------------------------------------------------------------------*/

		return "sign-in";
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
