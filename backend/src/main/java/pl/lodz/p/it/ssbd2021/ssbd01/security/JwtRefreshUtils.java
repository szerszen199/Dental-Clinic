package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.inject.Inject;
import java.text.ParseException;

public class JwtRefreshUtils extends JwtUtilsAbstract {

    @Inject
    private PropertiesLoader propertiesLoader;

    /**
     * Generuje token JWT na potrzeby odświeżenia tokenu Jwt.
     *
     * @param username username
     * @return JWT token
     */
    public String generateJwtTokenForUser(String username) {
        return super.generateJwtTokenForUsername(username);
    }

    @Override
    protected Long getJwtExpiration() {
        return propertiesLoader.getRefreshJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getRefreshJwtSecret();
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę odświeżenia tokenu Jwt.
     *
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return super.getUserNameFromJwtToken(token);
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby odświeżenia tokenu Jwt.
     *
     * @param tokenToValidate JWT token
     * @return boolean
     */

    public boolean validateJwtToken(String tokenToValidate) {
        return super.validateJwtToken(tokenToValidate);
    }
}
