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
import java.text.ParseException;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

/**
 * Typ Jwt utils.
 */
@Stateless
public class JwtEmailConfirmationUtils extends JwtUtilsAbstract {

    @Inject
    private PropertiesLoader propertiesLoader;

    private String registrationConfirmationJwtSecret;
    private Long registrationConfirmationJwtExpirationMs;

    @PostConstruct
    private void init() {
        registrationConfirmationJwtSecret = propertiesLoader.getConfirmationJwtSecret();
        registrationConfirmationJwtExpirationMs = propertiesLoader.getConfirmationJwtExpiration();
    }

    /**
     * Generuje token JWT na potrzeby weryfikacji rejestracji.
     *
     * @param username username
     * @return JWT token
     */
    public String generateRegistrationConfirmationJwtTokenForUser(String username) {
        return super.generateJwtTokenForUsername(username);
    }

    @Override
    protected Long getJwtExpiration() {
        return propertiesLoader.getConfirmationJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getConfirmationJwtSecret();
    }

    /**
     * Pobiera login użytkownika z tokenu JWT wydanego na potrzebę weryfikacji konta po rejestracji.
     * @param token JWT token
     * @return Login użytkownika o zadanym tokenie
     * @throws ParseException ParseException
     */
    public String getUserNameFromJwtToken(String token) throws ParseException {
        return super.getUserNameFromJwtToken(token);
    }

    /**
     * Metoda sprawdzająca token jwt na potrzeby weryfikacji konta poprzez email.
     * @param tokenToValidate JWT token
     * @return boolean
     */

    public boolean validateRegistrationConfirmationJwtToken(String tokenToValidate) {
        return super.validateJwtToken(tokenToValidate);
    }


}
