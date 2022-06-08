package xyz.morlotti.lemur.security.filter;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import lombok.extern.log4j.*;

import org.springframework.web.filter.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.web.authentication.*;

import org.springframework.security.core.context.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;

import xyz.morlotti.lemur.security.jwt.*;
import xyz.morlotti.lemur.security.service.*;

@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private JwtUtils jwtUtils;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException
	{
		Cookie[] cookies = request.getCookies();

		if(cookies != null)
		{
			for(Cookie cookie: cookies)
			{
				if(JwtUtils.TOKEN_COOKIE_NAME.equals(cookie.getName()))
				{
					String token = cookie.getValue();

					Optional<String> optional = jwtUtils.validateJwtToken(token);

					if(optional.isPresent())
					{
						UserDetails userDetails = userDetailsService.loadUserByUsername(optional.get());

						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

						SecurityContextHolder.getContext().setAuthentication(authentication);
					}

					break;
				}
			}
		}

		filterChain.doFilter(request, response);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
