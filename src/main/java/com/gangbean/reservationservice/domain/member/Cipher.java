package com.gangbean.reservationservice.domain.member;

import java.security.NoSuchAlgorithmException;

public interface Cipher {

    String encryption(String plain) throws NoSuchAlgorithmException;

}
