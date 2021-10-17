package xyz.morlotti.lemur.security.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${app.username}")
	private String appUsername;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${app.password}")
	private String appPassword;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		if(!appUsername.equals(username))
		{
			throw new UsernameNotFoundException("Username `" + username + "` not found");
		}

		return UserDetailsImpl.build(appUsername, appPassword);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
