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

import xyz.morlotti.lemur.security.jwt.JwtUtils;
import xyz.morlotti.lemur.controllers.bean.Credentials;

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
	public ModelAndView signIn1(ModelMap model)
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		model.addAttribute("year", year);

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

			model.addAttribute("errorMessage", e.getMessage());

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
