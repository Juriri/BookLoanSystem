package com.bookloansystem.backend.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
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

    //###################################################
    // 최소, 최대 글자 유효성 검증 (한글, 영어, 숫자, 특수문자 포함)
    public static boolean isRegexBook(String target, int min, int max) {
        // 정규표현식 패턴: 한글, 영어, 숫자, 특수문자 포함
        String regexPattern = "^[가-힣a-zA-Z0-9!@#$%^&*(),.?\":{}|<>\\s]*$";

        // 최소 글자 수, 최대 글자 수 확인
        if (target.length() < min || target.length() > max) {
            return false;
        }

        // 패턴 확인
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(target);

        return matcher.matches();
    }

    // Date 형식 유효성 검증
    public static boolean isValidDate (String dateString) throws ParseException {
        // 원하는 날짜 형식에 맞게 포맷을 지정
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // Strict 모드를 사용하여 더 엄격한 검증을 수행
        sdf.setLenient(false);

        try {
            // 파싱을 시도하고 예외가 발생하지 않으면 유효한 날짜 형식
            Date date = Date.valueOf(dateString);
            String formattedDate = sdf.format(date);

            Date parsedDate = Date.valueOf(formattedDate);

            if (!date.equals(parsedDate)) {
                return false;
            }

            int year = getYearFromSqlDate(date);
            int currentYear = getCurrentYear();

            return year >= 1900 && year <= currentYear;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static int getYearFromSqlDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.parseInt(sdf.format(date));
    }

    private static int getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.parseInt(sdf.format(new Date(System.currentTimeMillis())));
    }
}

