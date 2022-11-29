package com.communitygaming.security.jwt;

import com.communitygaming.constant.KeyConstant;
import com.communitygaming.dto.AuthToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.communitygaming.constant.KeyConstant.AUTHORITIES_KEY;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private Key key;

	@Value("${jwt.secret}")
	private String secret;

	public static final long JWT_TOKEN_1_HOURS = 24 * 60 * 60 * 1000L;

    @Override
	public void afterPropertiesSet() {
		byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public AuthToken createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));

		Date validity = new Date(System.currentTimeMillis() + JWT_TOKEN_1_HOURS);

        String jwtTokenString = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity).compact();
		return new AuthToken(jwtTokenString, validity.getTime());
    }

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		
		if(token.startsWith(KeyConstant.TOKEN_PREFIX))
		{
			token=token.substring(7);
		}
		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

		Collection<? extends GrantedAuthority> authorities = Arrays
				.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());

		User principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String authToken) {
		
		if(authToken.startsWith(KeyConstant.TOKEN_PREFIX)) {
			authToken = authToken.substring(7);
		}
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT signature. jwtToken:{}", authToken);
			log.trace("Invalid JWT signature trace", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT token. jwtToken:{}", authToken);
			log.trace("Expired JWT token trace", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT token. jwtToken:{}", authToken);
			log.trace("Unsupported JWT token trace", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT token compact of handler are invalid. jwtToken:{}", authToken);
			log.trace("JWT token compact of handler are invalid trace", e);
		}
		return false;
	}
}
