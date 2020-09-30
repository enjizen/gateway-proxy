package com.tua.wanchalerm.gateway.service;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@Slf4j
public class AESService {

    public String decrypt(String cipherText, String key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {

        val secretKey = getKey(key);
        val decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] v = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText);
        byte[] decodedByte = decryptCipher.doFinal(v);
        return new String(decodedByte);

    }

    public String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            val secretKey = getKey(secret);
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            log.error("Error while encrypt AES ",e );
        }
        return null;
    }

    private SecretKey getKey(String myKey) {
        return new SecretKeySpec(myKey.getBytes(), "AES");
    }
}
