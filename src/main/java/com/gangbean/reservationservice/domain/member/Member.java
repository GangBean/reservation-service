package com.gangbean.reservationservice.domain.member;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;

@Builder
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String loginId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "password_id")
    private Password password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_id")
    private Email email;

    @Column(nullable = false)
    private String name;

    @NonNull
    @Enumerated(value = EnumType.STRING)
    private Role role;

    public Member() {}

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

    public Long id() {
        return id;
    }

    public Email email() {
        return email;
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
