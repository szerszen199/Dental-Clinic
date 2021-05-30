package pl.lodz.p.it.ssbd2021.ssbd01.security;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;


@Stateless
public class JwtResetPasswordConfirmation extends JwtUtilsAbstract {

    @Inject
    private PropertiesLoader propertiesLoader;

    @Override
    public String parseAuthJwtFromHttpServletRequest(HttpServletRequest request) {
        return super.parseAuthJwtFromHttpServletRequest(request);
    }

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
        return propertiesLoader.getResetPasswordConfirmationJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getResetPasswordConfirmationJwtSecret();
    }

    @Override
    public String getVersionAndNameFromJwtToken(String token) throws ParseException {
        return super.getVersionAndNameFromJwtToken(token);
    }

    @Override
    public String generateJwtTokenForUsernameAndVersion(String username, long version) {
        return super.generateJwtTokenForUsernameAndVersion(username, version);
    }


}
