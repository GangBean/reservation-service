package com.gangbean.reservationservice.domain.member;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import lombok.Builder;

@Entity
public class Password {
    public static final String VALID_PATTERN = "^[0-9a-zA-Z!@#$%^&]{8,20}$";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private Cipher cipher;

    @Column(nullable = false)
    private String cipherName;

    @Column(nullable = false)
    private String password;

    public Password() {}

    public Password(Cipher cipher, String password) {
        this.cipher = cipher;
        this.cipherName = nameOf(cipher);
        this.password = notBlack(password);
    }

    @Builder
    public Password(Long id, String cipherName, String password) {
        this.id = id;
        this.cipher = cipherInstanceOf(cipherName);
        this.cipherName = cipherName;
        this.password = notBlack(password);
    }

    @PostLoad
    public void connectCipher() {
        if (cipher == null && !cipherName.isBlank()) {
            cipher = cipherInstanceOf(cipherName);
        }
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
        return Objects.equals(id, password1.id) && Objects.equals(cipher,
            password1.cipher) && Objects.equals(cipherName, password1.cipherName)
            && Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cipher, cipherName, password);
    }

    @Override
    public String toString() {
        return "Password{" +
            "id=" + id +
            ", cipher=" + cipher +
            ", cipherName='" + cipherName + '\'' +
            ", password='" + password + '\'' +
            '}';
    }

    private String nameOf(Cipher cipher) {
        return cipher.getClass().getName();
    }

    private Cipher cipherInstanceOf(String cipherName) {
        try {
            return (Cipher) Class.forName(cipherName)
                .getDeclaredMethod("getInstance")
                .invoke(null,null);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String notBlack(String password) {
        if (password == null || password.isBlank()) {
            throw new RuntimeException("비밀번호는 빈 값일 수 없습니다.");
        }
        return password;
    }
}
