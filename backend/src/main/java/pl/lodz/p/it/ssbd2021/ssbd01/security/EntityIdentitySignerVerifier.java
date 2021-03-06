package pl.lodz.p.it.ssbd2021.ssbd01.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;

/**
 * Klasa tworząca i weryfikująca podpisy dla obiektów.
 */
@ApplicationScoped
public class EntityIdentitySignerVerifier extends JwtUtilsAbstract implements Serializable {

    @Inject
    private PropertiesLoader propertiesLoader;


    /**
     * Metoda weryfikaującja poprawność podpisu obiektu.
     *
     * @param tagValue wartość podpisu
     * @return wartość bool czy podpisz jest poprawny
     */
    public boolean validateEntitySignature(String tagValue) {
        try {
            tagValue = tagValue.replaceAll("\"", "");
            final JWSObject jwsObject = JWSObject.parse(tagValue);
            final JWSVerifier verifier = new MACVerifier(getJwtSecret());
            return jwsObject.verify(verifier);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda weryfikujkąca poprawność zawartości obiektu na podstawie podpisu.
     *
     * @param tagValue       wartość podpisu
     * @param signableEntity obiekt weryfikowany
     * @return wartosć bool czy obiekt nie został zmieniony
     */
    public boolean verifyEntityIntegrity(String tagValue, SignableEntity signableEntity) {
        try {
            final String ifMatchHeaderValue = JWSObject.parse(tagValue).getPayload().toString()
                    .replaceAll("\"", "")
                    .replaceAll(",", ", ")
                    .replaceAll(":", "=");
            final String signablePayloadValue = signableEntity.getPayload().toString();
            return signablePayloadValue.equals(ifMatchHeaderValue);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda tworząca podpis dla obiektu.
     *
     * @param signableEntity obiekt dla którego podpis ma być utworzony
     * @return podpis obiektu
     */
    public String sign(SignableEntity signableEntity) {
        try {
            JWSSigner signer = new MACSigner(getJwtSecret());
            String jsonObject = new JSONObject(signableEntity.getPayload()).toString();
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jsonObject));
            jwsObject.sign(signer);

            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return "Etag error";
    }

    @Override
    protected Long getJwtExpiration() {
        return propertiesLoader.getJwtExpiration();
    }

    @Override
    protected String getJwtSecret() {
        return propertiesLoader.getEtagSecret();
    }
}
