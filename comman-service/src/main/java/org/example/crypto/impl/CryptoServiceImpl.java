package org.example.crypto.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.crypto.CryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

@Component
@Slf4j
public class CryptoServiceImpl implements CryptoService {

    private static final String SHA_256 = "SHA-256";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    @Value("${software.internal}")
    private String cryptoSecretKey;
    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    @PostConstruct
    private void configureCrypto() throws Exception {
        var key = cryptoSecretKey.getBytes(StandardCharsets.UTF_8);
        final var sha = MessageDigest.getInstance(SHA_256);
        key = sha.digest(key);
        key = Arrays.copyOf(key, 32);
        final var secretKey = new SecretKeySpec(key, ALGORITHM);
        encryptionCipher = Cipher.getInstance(TRANSFORMATION);
        encryptionCipher.init(Cipher.ENCRYPT_MODE, secretKey);
        decryptionCipher = Cipher.getInstance(TRANSFORMATION);
        decryptionCipher.init(Cipher.DECRYPT_MODE, secretKey);
        log.info("Crypto cipher configured");
    }

    @Override
    public String encrypt(String rawData) {
        try {
            return Base64.getEncoder().encodeToString(encryptionCipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public String decrypt(String encrypted) {
        try {
            return new String(decryptionCipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }


}
