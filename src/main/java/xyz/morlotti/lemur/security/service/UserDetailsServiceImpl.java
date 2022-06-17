package xyz.morlotti.lemur.security.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import xyz.morlotti.lemur.service.ConfigService;

// Vérifie si le user existe et si password correct en lisant dans la config
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	/*----------------------------------------------------------------------------------------------------------------*/

	@Autowired
	ConfigService configService;

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		/*------------------------------------------------------------------------------------------------------------*/

		Map<String, String> config = configService.getConfig();

		String appUsername = config.getOrDefault("username", "admin");
		String appPassword = config.getOrDefault("password", "admin");

		/*------------------------------------------------------------------------------------------------------------*/

		if(!username.equals(appUsername))
		{
			throw new UsernameNotFoundException("Username `" + username + "` not found");
		}

		/*------------------------------------------------------------------------------------------------------------*/

		return UserDetailsImpl.build(appUsername, appPassword);

		/*------------------------------------------------------------------------------------------------------------*/
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
