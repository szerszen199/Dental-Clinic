package pl.lodz.p.it.ssbd2021.ssbd01.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jose.shaded.json.JSONObject;
import pl.lodz.p.it.ssbd2021.ssbd01.mok.dto.response.AccountInfoResponseDTO;
import pl.lodz.p.it.ssbd2021.ssbd01.utils.PropertiesLoader;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.text.ParseException;

/**
 * Klasa tworząca i weryfikująca podpisy dla obiektów.
 */
@ApplicationScoped
public class EntityIdentitySignerVerifier implements Serializable {

    @Inject
    private PropertiesLoader propertiesLoader;


    private static String ETAG_SECRET;

    /**
     * Metoda inicjalizująca zmienną ETAG_SECRET.
     */
    @PostConstruct
    private void init() {
        ETAG_SECRET = propertiesLoader.getEtagSecret();
    }

    /**
     * Metoda weryfikaującja poprawność podpisu obiektu.
     * @param tagValue wartość podpisu
     * @return wartość bool czy podpisz jest poprawny
     */
    public static boolean validateEntitySignature(String tagValue) {
        try {
            JWSObject jwsObject = JWSObject.parse(tagValue);
            JWSVerifier verifier = new MACVerifier(ETAG_SECRET);
            return jwsObject.verify(verifier);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda weryfikujkąca poprawność zawartości obiektu na podstawie podpisu.
     * @param tagValue wartość podpisu
     * @param signableEntity obiekt weryfikowany
     * @return wartosć bool czy obiekt nie został zmieniony
     */
    public static boolean verifyEntityIntegrity(String tagValue, SignableEntity signableEntity) {
        try {
            final String ifMatchHeaderValue = JWSObject.parse(tagValue).getPayload().toString();
            final String signablePayloadValue = signableEntity.getPayload().toString();
            return validateEntitySignature(tagValue) && ifMatchHeaderValue.equals(signablePayloadValue);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Metoda tworząca podpis dla obiektu.
     * @param signableEntity  obiekt dla którego podpis ma być utworzony
     * @return podpis obiektu
     */
    public String sign(SignableEntity signableEntity) {
        try {
            JWSSigner signer = new MACSigner(ETAG_SECRET);
            String jsonObject = new JSONObject(signableEntity.getPayload()).toString();
            JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(jsonObject));
            jwsObject.sign(signer);
            return jwsObject.serialize();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return "Etag error";
    }
}
