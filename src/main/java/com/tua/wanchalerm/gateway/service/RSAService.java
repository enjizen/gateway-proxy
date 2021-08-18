package com.tua.wanchalerm.gateway.service;

import com.tua.wanchalerm.gateway.config.KeyConfig;
import lombok.val;
import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

@Service
public class RSAService {
    @Autowired
    private KeyConfig keyConfig;

    public String decrypt(String encryptedText) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, IOException, InvalidKeySpecException, NoSuchProviderException {
        val bytes = Base64.decodeBase64(encryptedText);
        val cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey());
        val decryptedText = cipher.doFinal(bytes);
        return new String(decryptedText, StandardCharsets.UTF_8);
    }

    private PrivateKey getPrivateKey() throws NoSuchProviderException, NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        val keyBytes =  readPemObject(keyConfig.getRsaPrivateKey()).getContent();
        val spec = new PKCS8EncodedKeySpec(keyBytes);
        val kf = KeyFactory.getInstance("RSA", "BC");
        return kf.generatePrivate(spec);
    }

    private PemObject readPemObject(String data) throws IOException {
        try (PemReader pemReader = new PemReader(new InputStreamReader(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8))))) {
            return pemReader.readPemObject();
        }
    }

}
