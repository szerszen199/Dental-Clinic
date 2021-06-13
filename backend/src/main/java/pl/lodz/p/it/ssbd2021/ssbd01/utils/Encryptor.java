package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * Klasa do enkrypcji i deskrypcji tekstu za pomocą klucza symetrycznego.
 */
public class Encryptor {

    private final PropertiesLoader propertiesLoader;

    public Encryptor(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    /**
     * Szyfrowanie wiadomości.
     *
     * @param message wiadomość do zaszyfrowania
     * @return Zaszyfrowana wiadomość
     * @throws NoSuchPaddingException    wyjątek typu NoSuchPaddingException
     * @throws NoSuchAlgorithmException  wyjątek typu NoSuchAlgorithmException
     * @throws IllegalBlockSizeException wyjątek typu IllegalBlockSizeException
     * @throws BadPaddingException       wyjątek typu BadPaddingException
     * @throws InvalidKeyException       wyjątek typu InvalidKeyException
     */
    public String encryptMessage(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES");
        byte[] decodedKey = propertiesLoader.getCipherKey().getBytes(StandardCharsets.UTF_8);
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        return new String(cipher.doFinal(messageBytes), StandardCharsets.UTF_8);
    }

    /**
     * Odszyfrowanie wiadomości.
     *
     * @param encryptedMessage wiadomość do odszyfrowania
     * @return Odszyfrowana wiadomość
     * @throws NoSuchPaddingException    wyjątek typu NoSuchPaddingException
     * @throws NoSuchAlgorithmException  wyjątek typu NoSuchAlgorithmException
     * @throws IllegalBlockSizeException wyjątek typu IllegalBlockSizeException
     * @throws BadPaddingException       wyjątek typu BadPaddingException
     * @throws InvalidKeyException       wyjątek typu InvalidKeyException
     */
    public String decryptMessage(String encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] decodedKey = Base64.getDecoder().decode(propertiesLoader.getCipherKey());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(encryptedMessage.getBytes()));
    }

}
