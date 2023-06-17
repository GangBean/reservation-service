package com.gangbean.reservationservice.domain.member

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class CipherSha512Test extends Specification {

    @Shared Cipher cipher = null

    def setupSpec() {
        cipher = CipherSha512.getInstance()
    }

    def "CipherSha512는 싱글톤 객체입니다"() {
        expect:
        cipher === CipherSha512.getInstance()
    }

    @Unroll
    def "비슷한 평문의 암호문은 비슷하지 않아야 합니다_평문의 편집거리가 암호문의 편집거리보다 작아야 합니다"(String a, String b) {
        expect:
        editDistance(a, b) <= editDistance(cipher.encryption(a), cipher.encryption(b))

        where:
        a | b
        "a" | "ab"
        "ab" | " ab"
        "ab c" | "abc"
        "ab_c" | "a_bc"
        "ab한" | "a한b"
    }

    @Unroll
    def "서로 다른 문자열을 암호화한 결과는 다릅니다"(String plain, String other) {
        expect:
        cipher.encryption(plain) != cipher.encryption(other)

        where:
        plain | other
        "a" | "ab"
        "ab" | " ab"
        "ab c" | "abc"
        "ab_c" | "a_bc"
        "ab한" | "a한b"
    }

    @Unroll
    def "동일한 문자열을 암호화한 결과는 동일합니다"(String plain) {
        expect:
        cipher.encryption(plain) == cipher.encryption(new String(plain))

        where:
        plain << ["" , "1234" , "abc" , "한글" , "!@#\$%^&*()_+" , "ABCDEF" , "      "]
    }

    @Unroll
    def "입력된 문자열을 암호화한 128자리의 암호문을 리턴해줍니다"(String plain) {
        expect:
        cipher.encryption(plain).length() == 128

        where:
        plain << ["" , "1234" , "abc" , "한글" , "!@#\$%^&*()_+" , "ABCDEF" , "      "]
    }

    int editDistance(String a, String b) {
        int count = 0;

        String s = (a.length() > b.length()) ? b : a;
        String l = s.equals(a) ? b : a;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != l.charAt(i)) {
                count++;
            }
        }

        count += l.length() - s.length();

        return count;
    }
}
