package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Klasa do enkrypcji i deskrypcji tekstu za pomocą klucza symetrycznego.
 */
public class Encryptor {

    private final PropertiesLoader propertiesLoader;
    private SecretKey secretKey;

    /**
     * Tworzy nową instancję klasy Encryptor.
     *
     * @param propertiesLoader properties loader
     */
    public Encryptor(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
        this.generateSecretKey();
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
    public byte[] encryptMessage(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(propertiesLoader.getCipherType());
        cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
        return cipher.doFinal(message.getBytes(StandardCharsets.UTF_8));
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
    public String decryptMessage(byte[] encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance(propertiesLoader.getCipherType());
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
        return new String(cipher.doFinal(encryptedMessage), StandardCharsets.UTF_8);
    }

    /**
     * Inicjalizuje klucz do szyfrowania i odszyfrowywania.
     */
    private void generateSecretKey() {
        byte[] decodedKey = propertiesLoader.getCipherKey().getBytes(StandardCharsets.UTF_8);
        this.secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, propertiesLoader.getCipherType());
    }

}
