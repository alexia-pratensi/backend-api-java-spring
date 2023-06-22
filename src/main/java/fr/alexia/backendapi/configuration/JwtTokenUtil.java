package fr.alexia.backendapi.configuration;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import static java.lang.String.format;

@Component
public class JwtTokenUtil {
	//configurations spécifiques pour la génération et la validation des jetons JWT
	private final String jwtSecret = "zdtlD3JK56m6wTTgsNFhqzjqP";
	private final String jwtIssuer = "fr.alexia";

	private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
	//génère un jeton d'accès JWT en utilisant les informations de l'utilisateur fourni (algorithme de hachage (HS512))
	public String generateAccessToken(User user) {
		// contient le nom d'utilisateur, l'émetteur (iss), la date d'émission (iat) et la date d'expiration (exp).
		return Jwts.builder()
				.setSubject(format("%s", user.getUsername()))
				.setIssuer(jwtIssuer).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	// extrait le nom d'utilisateur à partir du jeton JWT (jwtSecret)
	public String getUsername(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getSubject().split(",")[0];
	}
	
	//récupère la date d'expiration du jeton JWT. 
	//vérifie la signature du jeton (jwtSecret)
	//obtient les revendications (claims) du jeton, qui contiennent la date d'expiration
	public Date getExpirationDate(String token) {
		Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		return claims.getExpiration();
	}
	
	//vérifie la signature du jeton (jwtSecret)
	//détecte les éventuelles exceptions lors de l'analyse du jeton
	public boolean validate(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature - {}", ex.getMessage());
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token - {}", ex.getMessage());
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token - {}", ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token - {}", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty - {}", ex.getMessage());
		}
		return false;
	}
	
}