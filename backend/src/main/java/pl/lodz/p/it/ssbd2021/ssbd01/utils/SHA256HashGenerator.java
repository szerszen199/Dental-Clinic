package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.ManagedBean;
import pl.lodz.p.it.ssbd2021.ssbd01.exceptions.InvalidHashAlgorithmException;

@ManagedBean
public class SHA256HashGenerator implements HashGenerator {

    /**
     * Tworzy nową instancję klasy SHA256HashGenerator.
     */
    public SHA256HashGenerator() {
    }

    @Override
    public String generateHash(String input) {
        try {
            return bytesToHex(MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new InvalidHashAlgorithmException();
        }
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
