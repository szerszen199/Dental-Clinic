package pl.lodz.p.it.ssbd2021.ssbd01.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import java.util.Date;
import javax.ejb.Stateless;

/**
 * Typ Jwt utils.
 */
@Stateless
public class JwtUtils {

    private final String registrationConfirmationJwtSecret = PropertiesLoader.getConfirmationJwtSecret();
    private final Long registrationConfirmationJwtExpirationMs = PropertiesLoader.getConfirmationJwtExpiration();

    /**
     * Generuje token JWT na potrzeby weryfikacji rejestracji.
     *
     * @param username username
     * @return JWT token
     */
    public String generateRegistrationConfirmationJwtTokenForUser(String username) {

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + registrationConfirmationJwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, registrationConfirmationJwtSecret)
                .compact();
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę weryfikacji konta po rejestracji.
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     */
    public String getUserNameFromRegistrationConfirmationJwtToken(String token) {
        return Jwts.parser().setSigningKey(registrationConfirmationJwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby weryfikacji konta poprzez email.
     * @param authToken JWT token
     * @return boolean
     */
    public boolean validateRegistrationConfirmationJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(registrationConfirmationJwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            // TODO: 18.04.2021 :
            ex.getStackTrace();
        }
        return false;
    }

}
