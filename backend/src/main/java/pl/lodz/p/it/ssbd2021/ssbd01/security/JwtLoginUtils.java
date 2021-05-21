package pl.lodz.p.it.ssbd2021.ssbd01.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.text.ParseException;
import java.util.Date;

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
