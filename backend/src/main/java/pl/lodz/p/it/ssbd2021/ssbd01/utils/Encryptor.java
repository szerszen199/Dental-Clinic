package pl.lodz.p.it.ssbd2021.ssbd01.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {

    @Inject
    private PropertiesLoader propertiesLoader;

    public String encryptMessage(String message) throws NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] decodedKey = Base64.getDecoder().decode(propertiesLoader.getCipherKey());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] messageBytes = Base64.getDecoder().decode(message);
        return Base64.getEncoder().encodeToString(cipher.doFinal(messageBytes));
    }

    public byte[] decryptMessage(String encryptedMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        byte[] decodedKey = Base64.getDecoder().decode(propertiesLoader.getCipherKey());
        SecretKey secretKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedMessage.getBytes());
    }

}
