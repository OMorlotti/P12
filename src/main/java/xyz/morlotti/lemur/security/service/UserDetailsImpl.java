package xyz.morlotti.lemur.security.service;

import java.util.List;
import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImpl implements UserDetails
{
	/*----------------------------------------------------------------------------------------------------------------*/

	private final String username;

	/*----------------------------------------------------------------------------------------------------------------*/

	private final String password;

	/*----------------------------------------------------------------------------------------------------------------*/

	private final Collection<? extends GrantedAuthority> authorities;

	/*----------------------------------------------------------------------------------------------------------------*/

	public UserDetailsImpl(String username, String password, Collection<? extends GrantedAuthority> authorities)
	{
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static UserDetailsImpl build(String username, String password)
	{
		List<GrantedAuthority> authorities = Collections.singletonList(
			new SimpleGrantedAuthority("ADMIN")
		);

		return new UserDetailsImpl(
			username,
			password,
			authorities
		);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.authorities;
	}

	public boolean hasAuthority(String authority)
	{
		return this.authorities.stream().anyMatch(x -> x.toString().equals(authority));
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public String getUsername()
	{
		return this.username;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public String getPassword()
	{
		return this.password;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public boolean isEnabled()
	{
		return true;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public boolean isAccountNonExpired()
	{
		return true;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public boolean isAccountNonLocked()
	{
		return true;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	@Override
	public boolean isCredentialsNonExpired()
	{
		return true;
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
