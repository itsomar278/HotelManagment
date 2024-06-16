package Omar.HotelWebServer.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.util.Date;

@Component
public class TokenProvider {

    @Value("${jwt.tokenValidityMilliseconds}")
    private long tokenValidityMilliseconds;

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String provideToken(Authentication authentication) {
        String username = authentication.getName();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + tokenValidityMilliseconds);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, DatatypeConverter.parseBase64Binary(jwtSecret))
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            System.out.println("Invalid JWT token");
        }  catch (SignatureException e) {
            System.out.println("Invalid JWT signature");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid JWT format");
        } catch (ExpiredJwtException e) {
            System.out.println("Expired JWT JWT");
        } catch (Exception e) {
            System.out.println("Unknown JWT exception occurred"+ e.getMessage());
        }
        throw new AuthenticationCredentialsNotFoundException("You need to re-login to get a new token :)");
    }


}