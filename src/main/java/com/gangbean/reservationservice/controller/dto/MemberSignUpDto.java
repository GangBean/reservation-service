package com.gangbean.reservationservice.controller.dto;

import com.gangbean.reservationservice.domain.member.Email;
import com.gangbean.reservationservice.domain.member.Password;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
public class MemberSignUpDto {
    public static final String INVALID_PASSWORD_PATTERN_MESSAGE = "패스워드는 숫자, 영문 소/대문자, !,@,#,$,%,^,& 으로 최소 8자리, 최대 20자리로 설정가능합니다.";
    public static final String INVALID_EMAIL_PATTERN_MESSAGE = "이메일 형식을 확인해주세요.";

    @NotBlank(message = "아이디는 빈 값일 수 없습니다.")
    private final String loginId;

    @NotBlank(message = "비밀번호는 빈 값일 수 없습니다.")
    @Pattern(regexp = Password.VALID_PATTERN, message = INVALID_PASSWORD_PATTERN_MESSAGE)
    private final String password;

    @NotBlank(message = "이메일은 빈 값일 수 없습니다.")
    @Pattern(regexp = Email.VALID_PATTERN, message = INVALID_EMAIL_PATTERN_MESSAGE)
    private final String email;

    @NotBlank(message = "이름은 빈 값일 수 없습니다.")
    private final String name;
}
