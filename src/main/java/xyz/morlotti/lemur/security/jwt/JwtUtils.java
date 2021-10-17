package xyz.morlotti.lemur.security.jwt;

import lombok.extern.log4j.*;

import java.util.*;
import java.security.*;

import javax.crypto.spec.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

@Log4j2
@Component
public class JwtUtils
{
	/*----------------------------------------------------------------------------------------------------------------*/

	public static final String TOKEN_COOKIE_NAME = "token";

	/*----------------------------------------------------------------------------------------------------------------*/

	@Value("${app.jwt.secret}")
	private String jwtSecret;

	@Value("${app.jwt.expiration-ms}")
	private int jwtExpirationMs;

	/*----------------------------------------------------------------------------------------------------------------*/

	private Key buildJwtKey()
	{
		return new SecretKeySpec(Base64.getDecoder().decode(jwtSecret), SignatureAlgorithm.HS256.getJcaName());
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public String generateJwtToken(String login)
	{
		Date now = new Date(), after = new Date(now.getTime() + jwtExpirationMs);

		return Jwts.builder()
		           .claim("login", login)
		           .setIssuedAt(now)
		           .setExpiration(after)
		           .signWith(buildJwtKey())
		           .compact()
		;
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public Optional<String> validateJwtToken(String token)
	{
		if(token != null)
		{
			try
			{
				return Optional.of(Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().get("login", String.class));
			}
			catch(DecodingException e)
			{
				log.error("Broken JWT token: {}", e.getMessage());
			}
			catch(MalformedJwtException e)
			{
				log.error("Invalid JWT token: {}", e.getMessage());
			}
			catch(ExpiredJwtException e)
			{
				log.error("JWT token is expired: {}", e.getMessage());
			}
			catch(UnsupportedJwtException e)
			{
				log.error("JWT token is unsupported: {}", e.getMessage());
			}
			catch(IllegalArgumentException e)
			{
				log.error("JWT claims string is empty: {}", e.getMessage());
			}
		}

		return Optional.empty();
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static void createTokenCookie(HttpServletResponse httpServletResponse, String token)
	{
		Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, token);
		cookie.setMaxAge(60 * 60 * 24 * 30);
		cookie.setPath("/");

		httpServletResponse.addCookie(cookie);
	}

	/*----------------------------------------------------------------------------------------------------------------*/

	public static void deleteTokenCookie(HttpServletResponse httpServletResponse)
	{
		Cookie cookie = new Cookie(TOKEN_COOKIE_NAME, null);
		cookie.setMaxAge(60 * 60 * 24 * 30);
		cookie.setPath("/");

		httpServletResponse.addCookie(cookie);
	}

	/*----------------------------------------------------------------------------------------------------------------*/
}
