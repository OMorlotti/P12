package xyz.morlotti.lemur.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import xyz.morlotti.lemur.controllers.bean.Credentials;
import xyz.morlotti.lemur.security.jwt.JwtUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

@Controller
public class AuthController
{
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/sign-in", method = RequestMethod.GET)
	public String signIn(Model model)
	{
		Calendar cal = Calendar.getInstance();

		int year = cal.get(Calendar.YEAR);

		model.addAttribute("year", year);

		return "sign-in";
	}

	@RequestMapping(value = "/sign-in", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView signIn2(@ModelAttribute("credentials") Credentials credentials, Model model, HttpServletResponse httpServletResponse)
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

			return new ModelAndView("redirect:/sign-in");
		}
	}

	@RequestMapping(value = "/sign-out", method = RequestMethod.GET)
	public String signOut(Model model, HttpServletResponse httpServletResponse)
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
}
