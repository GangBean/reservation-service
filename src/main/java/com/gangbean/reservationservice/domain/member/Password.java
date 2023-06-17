package com.gangbean.reservationservice.domain.member;

public class Password {

    private final String password;

    public Password(String password) {
        if (password == null || password.isBlank()) {
            throw new RuntimeException("비밀번호는 빈 값일 수 없습니다.");
        }
        this.password = password;
    }

    public boolean isSame(String input) {
        return password == input;
    }
}
