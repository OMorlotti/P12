package xyz.morlotti.lemur.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
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
