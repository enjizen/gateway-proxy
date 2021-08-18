package com.tua.wanchalerm.gateway.service;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
@DisplayName("AES Service")
public class AESServiceTest {

    @InjectMocks
    private AESService aesService;

    @Test
    @DisplayName("Decrypt success method should return plain text message")
    public void testDecrypt_decryptSuccess_methodShouldReturnPlainTextMessage() throws NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        val expectText = "Simple message";
        val plainTextKey = generateSecretKey();
        val cipherText = encrypt(expectText, plainTextKey);
        val result = aesService.decrypt(cipherText, plainTextKey);

        assertEquals(expectText, result);
    }

    @Test
    public void testEncrypt_encryptSuccess_methodShouldReturnPlainTextMessage() {
        val expectText = "Simple message";
        val plainTextKey = generateSecretKey();
        val cipher = aesService.encrypt(expectText, plainTextKey);
        val plainText = decrypt(cipher, plainTextKey);

        assertEquals(plainText, expectText);
    }


    private String encrypt(String strToEncrypt, String secret)
    {
        try
        {
            SecretKey secretKey = getKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }


    private String decrypt(String cipherText, String key)
    {
        try
        {
            SecretKey secretKey  = getKey(key);
            Cipher decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] v = Base64.getDecoder().decode(cipherText);
            byte[] decodedByte = decryptCipher.doFinal(v);
            return new String(decodedByte);
        }
        catch (Exception e)
        {
            System.out.println("Error whike decrypting: " + e.toString());
        }

        return  null;
    }


    private SecretKey getKey(String myKey) {
        return new SecretKeySpec(myKey.getBytes(), "AES");
    }

    private String generateSecretKey() {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( 16 );
        for( int i = 0; i < 16; i++ ) {
            sb.append( AB.charAt( rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
