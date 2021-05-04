package de.noah.onlinebankingapp;

import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Formatter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Hash implements Serializable {
    String text = null;
    public Hash(String text) {
        this.text = text;
    }
    public static String toHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);

        Formatter formatter = new Formatter(sb);
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        return sb.toString();
    }
    public String encode()
            throws SignatureException {

        try {
            String secretKey = "B9+-#*sNBsuzw83";
            Key sk = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
            Mac mac = Mac.getInstance(sk.getAlgorithm());
            mac.init(sk);
            final byte[] hmac = mac.doFinal(text.getBytes());
            return toHexString(hmac);
        } catch (NoSuchAlgorithmException e1) {
            // throw an exception or pick a different encryption method
            throw new SignatureException(
                    "error building signature, no such algorithm in device "
                            + "HmacSHA256");
        } catch (InvalidKeyException e) {
            throw new SignatureException(
                    "error building signature, invalid key " + "HmacSHA256");
        }
    }


}
