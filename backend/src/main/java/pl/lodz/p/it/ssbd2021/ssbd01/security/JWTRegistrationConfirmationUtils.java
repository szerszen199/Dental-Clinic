package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;

/**
 * Klasa Jwt potweirdzenia rejestracji narzędzia.
 */
@Stateless
public class JWTRegistrationConfirmationUtils extends JwtUtilsAbstract {

    @Inject
    private PropertiesLoader propertiesLoader;

    @Override
    public String generateJwtTokenForUsername(String username) {
        return super.generateJwtTokenForUsername(username);
    }

    @Override
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return super.getUserNameFromJwtToken(token);
    }

    @Override
    public boolean validateJwtToken(String tokenToValidate) {
        return super.validateJwtToken(tokenToValidate);
    }

    @Override
    protected Long getJwtExpiration() {
        return propertiesLoader.getConfirmationJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getConfirmationJwtSecret();
    }
}
