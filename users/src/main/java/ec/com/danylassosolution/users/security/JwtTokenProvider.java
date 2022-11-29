package ec.com.danylassosolution.users.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtTokenProvider {

	@Autowired
	private JwtConfig jwtConfig;

	public String generateJwt(final Authentication authentication, Map<String, Object> claims) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + jwtConfig.getExpiration());

		return Jwts.builder().setSubject(authentication.getName()).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes()).addClaims(claims).compact();

	}

	public boolean validateToken(final String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(authToken);
			return true;
		} catch (final SignatureException ex) {
			log.error("Invalid JWT signature");
		} catch (final MalformedJwtException ex) {
			log.error("Invalid JWT token");
		} catch (final ExpiredJwtException ex) {
			log.error("Expired JWT token");
		} catch (final UnsupportedJwtException ex) {
			log.error("Unsupported JWT token");
		} catch (final IllegalArgumentException ex) {
			log.error("JWT claims string is empty.");
		}
		return false;
	}
	
	public Claims getClaimsFromJWT(final String token) {
		return Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token).getBody();
	}
	
	public String generateJwtAsTempPassword() {
		
		Date now = new Date();
		Date expiryDate = new Date((now.getTime() + jwtConfig.getExpiration()) * 120L);
		return Jwts.builder()
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret())
				.compact();
	}
}
