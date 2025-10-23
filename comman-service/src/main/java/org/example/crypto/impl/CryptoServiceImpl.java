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

/**
 * Implementation of CryptoService using AES encryption.
 * This service provides symmetric encryption and decryption of strings using a secret key.
 * The secret key is derived from a user-defined string and hashed using SHA-256 to ensure fixed length.
 * AES/ECB/PKCS5Padding transformation is used for encryption.
 */
@Component
@Slf4j
public class CryptoServiceImpl implements CryptoService {

    private static final String SHA_256 = "SHA-256";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";

    /**
     * value pic from .yml
     */
    @Value("${software.internal}")
    private String cryptoSecretKey;

    private Cipher encryptionCipher;
    private Cipher decryptionCipher;

    /**
     * Configuration
     * Convert the secret string to bytes.
     * Hash the bytes using SHA-256 to get a 32-byte fixed-length key.
     * Initialize SecretKeySpec with AES algorithm.
     * Initialize encryption and decryption Cipher instances with AES/ECB/PKCS5Padding.
     */
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

    /**
     * @param rawData plain text data to encrypt
     * @return Base64 encoded encrypted string
     */
    @Override
    public String encrypt(String rawData) {
        try {
            return Base64.getEncoder().encodeToString(encryptionCipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            log.error("Encryption failed: {}", e.toString());
            return null;
        }
    }

    /**
     * @param encrypted Base64 encoded encrypted string
     * @return decrypted plain text string
     */
    @Override
    public String decrypt(String encrypted) {
        try {
            return new String(decryptionCipher.doFinal(Base64.getDecoder().decode(encrypted)));
        } catch (Exception e) {
            log.error("Decryption failed: {}", e.toString());
            return null;
        }
    }
}
