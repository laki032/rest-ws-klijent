package ws.client.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {

    private final static Logger log = Logger.getLogger(Encryptor.class.getName());
    private final static String KEY = "LazarVujadinovic"; // 128 bit key
    private final static String INIT_VECTOR = "RandomInitVector"; // 16 bytes IV

    private static String encrypt(String key, String initVector, String value) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error encrypting data! {0}", ex.getMessage());
            return value;
        }
    }

    private static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

            return new String(original);
        } catch (Exception ex) {
            log.log(Level.SEVERE, "Error decrypting data! {0}", ex.getMessage());
            return encrypted;
        }
    }

    public static String encrypt(String valueToEncrypt) {
        return encrypt(KEY, INIT_VECTOR, valueToEncrypt);
    }

    public static String decrypt(String encryptedValue) {
        return decrypt(KEY, INIT_VECTOR, encryptedValue);
    }

}