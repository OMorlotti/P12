package xyz.morlotti.lemur.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.http.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.authentication.builders.*;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import xyz.morlotti.lemur.security.filter.AuthTokenFilter;
import xyz.morlotti.lemur.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
	{
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers(HttpMethod.GET, "/sign-in", "/sign-out", "/static/**").permitAll()
			                    .antMatchers(HttpMethod.POST, "/sign-in", "/sign-out", "/static/**").permitAll()
			                    .antMatchers(HttpMethod.OPTIONS, "**").permitAll()
			.anyRequest().authenticated()
		    .and()
		    .exceptionHandling().authenticationEntryPoint(new AuthenticationEntryPoint() { // Déclenché lorsqu'une authentification est requise

				@Override
				public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e)
				{
					httpServletResponse.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

					httpServletResponse.setHeader("Location", "/sign-in");
				}
			});
		;

		http.addFilterBefore(authJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public AuthTokenFilter authJwtTokenFilter()
	{
		return new AuthTokenFilter();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new PasswordEncoder() {

			@Override
			public String encode(CharSequence rawPassword)
			{
				return rawPassword.toString();
			}

			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword)
			{
				return encodedPassword.equals(rawPassword.toString());
			}
		};
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
