package org.example.crypto;

public interface CryptoService {
    String encrypt(String rawData);

    String decrypt(String encrypted);
}
