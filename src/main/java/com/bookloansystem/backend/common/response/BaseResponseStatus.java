package com.bookloansystem.backend.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 200 : 요청 성공
     */
    SUCCESS(true, HttpStatus.OK.value(), "요청에 성공하였습니다."),


    /**
     * 400 : User Request, Response 오류
     */
    USERS_EMPTY_USERID(false, HttpStatus.BAD_REQUEST.value(), "회원 ID를 입력해주세요."),
    USERS_EMPTY_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "패스워드를 입력해주세요."),
    USERS_EMPTY_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일을 입력해주세요."),
    USERS_EMPTY_NAME(false, HttpStatus.BAD_REQUEST.value(), "이름을 입력해주세요."),

    BOOK_EMPTY_INFO(false, HttpStatus.BAD_REQUEST.value(), "공란을 확인해주세요."),

    USERS_NOT_EXISTS_USERNAME(false,HttpStatus.BAD_REQUEST.value(),"가입되지않은 회원입니다."),
    USERS_NOT_EXISTS_PASSWORD(false,HttpStatus.BAD_REQUEST.value(),"입력하신 정보가 올바르지않습니다."),

    USERS_INVALID_USERNAME(false, HttpStatus.BAD_REQUEST.value(), "아이디 형식을 확인해주세요."),
    USERS_INVALID_EMAIL(false, HttpStatus.BAD_REQUEST.value(), "이메일 형식을 확인해주세요."),
    USERS_INVALID_PASSWORD(false, HttpStatus.BAD_REQUEST.value(), "패스워드는 8-20자 사이 대/소문자+숫자 조합이여야 합니다.(특수문자가능)"),
    USERS_INVALID_NAME(false, HttpStatus.BAD_REQUEST.value(), "이름 형식을 확인해주세요."),

    POST_USERS_EXISTS_USERID(false,HttpStatus.BAD_REQUEST.value(),"중복된 ID입니다."),
    POST_USERS_EXISTS_EMAIL(false,HttpStatus.BAD_REQUEST.value(),"중복된 이메일입니다."),
    POST_BOOK_LOAN_EXISTS(false,HttpStatus.BAD_REQUEST.value(),"이미 대출한 도서입니다."),
    POST_BOOK_EXISTS(false,HttpStatus.BAD_REQUEST.value(),"이미 등록된 도서입니다."),
    BOOK_INVALID_ID(false,HttpStatus.BAD_REQUEST.value(),"해당 도서 ID가 존재하지않습니다."),
    BOOK_LOAN_NOT_EXISTS(false,HttpStatus.BAD_REQUEST.value(),"해당 도서 대출 이력이 존재하지않습니다."),
    BOOK_LESS_QUANTITY(false,HttpStatus.BAD_REQUEST.value(),"도서가 모두 대출중입니다."),
    BOOK_INVALID_CATEGORY(false,HttpStatus.BAD_REQUEST.value(),"잘못된 카테고리입니다.(인문/공학/에세이/예술)"),
    BOOK_INVALID_TITLE(false,HttpStatus.BAD_REQUEST.value(),"제목 입력을 다시 확인해주세요."),
    BOOK_INVALID_AUTHOR(false,HttpStatus.BAD_REQUEST.value(),"저자 입력을 다시 확인해주세요."),
    BOOK_INVALID_PUBLISHER(false,HttpStatus.BAD_REQUEST.value(),"출판사 입력을 다시 확인해주세요."),
    BOOK_INVALID_DATE(false,HttpStatus.BAD_REQUEST.value(),"잘못된 날짜 형식입니다. (YYYY-MM-DD)"),
    BOOK_INVALID_QUANTITY(false,HttpStatus.BAD_REQUEST.value(),"잘못된 수량입니다."),
    BOOK_INVALID_REGEX(false,HttpStatus.BAD_REQUEST.value(),"입력 형식을 다시 확인해주세요."),
    /**
     * 500 :  Database, Server 오류
     */
    DATABASE_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "서버와의 연결에 실패하였습니다."),
    PASSWORD_ENCRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "비밀번호 복호화에 실패하였습니다."),


    MODIFY_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저네임 수정 실패"),
    DELETE_FAIL_USERNAME(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"유저 삭제 실패"),
    MODIFY_FAIL_MEMO(false,HttpStatus.INTERNAL_SERVER_ERROR.value(),"메모 수정 실패"),
    UNEXPECTED_ERROR(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "예상치 못한 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}