package com.gangbean.reservationservice.domain.member;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CipherSha512 implements Cipher {

    public static final String ALGORITHM = "SHA-512";
    public static final String FORMAT = "%02x";

    public static CipherSha512 INSTANCE = null;

    private CipherSha512() {}

    public static CipherSha512 getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CipherSha512();
        }
        return INSTANCE;
    }

    @Override
    public String encryption(String plain) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);

        byte[] hashBytes = messageDigest.digest(plain.getBytes());

        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : hashBytes) {
            stringBuilder.append(String.format(FORMAT, b));
        }

        return stringBuilder.toString();
    }
}
