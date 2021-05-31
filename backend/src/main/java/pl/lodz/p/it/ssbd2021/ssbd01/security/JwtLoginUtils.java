package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;

/**
 * Klasa Jwt login narzędzia.
 */
@Stateless
public class JwtLoginUtils extends JwtUtilsAbstract {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Generuje token JWT na potrzeby weryfikacji rejestracji.
     *
     * @param username username
     * @return JWT token
     */
    public String generateJwtTokenForUser(String username) {
        return super.generateJwtTokenForUsername(username);
    }

    @Override
    protected Long getJwtExpiration() {
        return propertiesLoader.getJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getJwtSecret();
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę weryfikacji konta po rejestracji.
     *
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return super.getUserNameFromJwtToken(token);
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby weryfikacji konta poprzez email.
     *
     * @param tokenToValidate JWT token
     * @return boolean
     */

    public boolean validateJwtToken(String tokenToValidate) {
        return super.validateJwtToken(tokenToValidate);
    }


}
