package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
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
     * Generate registration confirmation jwt token for user string.
     *
     * @param username username
     * @return string
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
     * Pobiera pole user name from registration confirmation jwt token.
     *
     * @param token token
     * @return user name from registration confirmation jwt token
     */
    public String getUserNameFromRegistrationConfirmationJwtToken(String token) {
        return Jwts.parser().setSigningKey(registrationConfirmationJwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    /**
     * Validate registration confirmation jwt token boolean.
     *
     * @param authToken auth token
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
