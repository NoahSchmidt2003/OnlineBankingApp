package de.noah.onlinebankingapp;

import java.io.Serializable;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class AES256 implements Serializable {
    String text;
    String secretKey;
    public AES256(String text, String secretKey) {
        this.text = text;
        this.secretKey = secretKey;
    }
    public String encrypt() {
        byte[] raw;
        String encryptedString;
        SecretKeySpec skeySpec;
        byte[] encryptText = text.getBytes();
        Cipher cipher;
        System.out.println("size before " + secretKey.length());
        try {
            raw = secretKey.getBytes();
            raw = Arrays.copyOfRange(raw, 32, raw.length);
            System.out.println("size before " + raw.length);

            skeySpec = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
        }
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return encryptedString;
    }

    public String decrypt() {
        Cipher cipher;
        String encryptedString;
        byte[] encryptText = null;
        byte[] raw;
        SecretKeySpec skeySpec;
        try {
            raw = secretKey.getBytes();
            raw = Arrays.copyOfRange(raw, 32, raw.length);
            skeySpec = new SecretKeySpec(raw, "AES");
            encryptText = Base64.decodeBase64(text);
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            encryptedString = new String(cipher.doFinal(encryptText));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return encryptedString;
    }
}