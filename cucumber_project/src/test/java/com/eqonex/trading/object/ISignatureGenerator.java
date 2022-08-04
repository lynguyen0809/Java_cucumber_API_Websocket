package com.eqonex.trading.object;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface ISignatureGenerator {
    String HMAC_TYPE = "SHA384";
    String HMAC_SHA384_ALGORITHM = "HmacSHA384";


    static String signatureGenerator(String userSecret, String message){
        message = message.replaceAll("\\r\\n|\\r|\\n|\\s", "").replaceAll("\\\\","");
        return hmacSHA384(message, userSecret);
    }

    static String hmacSHA384(String s, String key) {
        return hexdigest(s.getBytes(), key.getBytes());
    }

    // convert data to HMAC-SHA384 as hexadecimal
    static String hexdigest(byte[] message, byte[] keyData){
        try {
            SecretKey key = new SecretKeySpec(keyData, HMAC_SHA384_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA384_ALGORITHM);
            mac.init(key);
            mac.update(message);
            byte[] hmac = mac.doFinal();
            return Hex.encodeHexString(hmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(String.format("Cannot Digest Message [%s] with secret [%s]", new String(message, StandardCharsets.UTF_8),new String(keyData, StandardCharsets.UTF_8)));
        }
    }
}
