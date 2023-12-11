package com.bookloansystem.backend.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    // 이메일 검증
    // 대문자, 숫자, 기호 중 최소 하나 이상 + '@' + 대문자, 숫자, 기호 중 최소 하나 이상 + '.' + 대문자 2-6자
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 아이디 유효성 검증 (영문 소문자로 시작, 영문 소문자와 숫자의 조합, 총 5~12자)
    public static boolean isRegexUsername(String target) {
        String regex = "^[a-z][a-z\\d]{4,11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 패스워드 유효성 검증 (8-20자, 영문 대/소문자, 숫자, 특수문자 조합)
    public static boolean isRegexPassword(String target) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    // 이름 유효성 검증 (1-20자, 한글)
    public static boolean isRegexName(String target) {
        String regex = "^[가-힝]{1,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
}

