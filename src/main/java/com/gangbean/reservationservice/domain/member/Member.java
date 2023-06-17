package com.gangbean.reservationservice.domain.member;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class Member {

    private final String loginId;

    private final Password password;

    private final Email email;

    private final String name;

    @NonNull
    private Role role;

    public boolean isPasswordMatch(String plain) throws NoSuchAlgorithmException {
        return password.isSame(plain);
    }

    public boolean isMember() {
        return role.isMember();
    }

    public boolean isManager() {
        return role.isManager();
    }

    public void becomeSeller() {
        role = Role.SELLER;
    }

    public boolean isSeller() {
        return role.isSeller();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(loginId, member.loginId) && Objects.equals(password,
            member.password) && Objects.equals(email, member.email)
            && Objects.equals(name, member.name) && role == member.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(loginId, password, email, name, role);
    }
}
