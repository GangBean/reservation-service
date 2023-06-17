package com.gangbean.reservationservice.domain.member;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import lombok.Builder;

@Builder
public class Password {

    public static final String VALID_PATTERN = "^[0-9a-zA-Z!@#$%^&]{8,20}$";

    private final Cipher cipher;
    private final String password;

    public Password(Cipher cipher, String password) {
        this.cipher = cipher;
        this.password = notBlack(password);
    }

    private String notBlack(String password) {
        if (password == null || password.isBlank()) {
            throw new RuntimeException("비밀번호는 빈 값일 수 없습니다.");
        }
        return password;
    }

    public boolean isSame(String input) throws NoSuchAlgorithmException {
        return password.equals(cipher.encryption(input));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Password password1 = (Password) o;
        return Objects.equals(cipher, password1.cipher) && Objects.equals(password,
            password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cipher, password);
    }
}
