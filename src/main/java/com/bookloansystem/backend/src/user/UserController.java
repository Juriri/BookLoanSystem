package com.bookloansystem.backend.src.user;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponse;
import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;
import static com.bookloansystem.backend.common.utils.ValidationRegex.*;
import com.bookloansystem.backend.src.user.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 회원가입 API
     * [POST] /signup
     * @param postUserReq 회원가입 모델
     * @return BaseResponse<String>
     */

    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<String> createUser(@RequestBody PostUserReq postUserReq) {
        if (validatePostUserReq(postUserReq)!= null) {
            return validatePostUserReq(postUserReq);
        } else {
            return new BaseResponse<>(userService.createUser(postUserReq));
        }
    }

    //회원가입 정보 공란과 이메일 정규식 여부 확인
    private BaseResponse<String> validatePostUserReq(PostUserReq postUserReq) {
        if (postUserReq.getUsername() == null) {
            throw new BaseException(USERS_EMPTY_USERID);
        }

        if (postUserReq.getPassword() == null) {
            throw new BaseException(USERS_EMPTY_PASSWORD);
        }

        if (postUserReq.getEmail() == null) {
            throw new BaseException(USERS_EMPTY_EMAIL);
        }

        //아이디 유효성 검증
        // 아이디 유효성 검증 (영문 소문자로 시작, 영문 소문자와 숫자의 조합, 총 5~12자)
        if(!isRegexUsername(postUserReq.getUsername())) {
            throw new BaseException(USERS_INVALID_USERNAME);
        }

        //패스워드 유효성 검증
        //(8-20자, 영문 대/소문자, 숫자, 특수문자 조합)
        if(!isRegexPassword(postUserReq.getPassword())) {
            throw new BaseException(USERS_INVALID_PASSWORD);
        }

        //이메일 유효성 검증
        // 대문자, 숫자, 기호 중 최소 하나 이상 + '@' + 대문자, 숫자, 기호 중 최소 하나 이상 + '.' + 대문자 2-6자
        if(!isRegexEmail(postUserReq.getEmail())){
            throw new BaseException(USERS_INVALID_EMAIL);
        }

        //이름 유효성 검증
        //한글 1-20자
        if(!isRegexName(postUserReq.getName())) {
            throw new BaseException(USERS_INVALID_NAME);
        }
        return null; // 유효성 검사 통과
    }

    /**
     * login API
     * [POST] /login
     * @param postLoginReq 로그인 모델
     * @return BaseResponse<String>
     */

    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<Boolean> loginUser(@RequestBody PostLoginReq postLoginReq) {
        if (validatePostLoginReq(postLoginReq)!= null) {
            return validatePostLoginReq(postLoginReq);
        } else {
            return new BaseResponse<>(userService.loginUser(postLoginReq));
        }
    }

    //로그인 정보 공란과 유효성 확인
    private BaseResponse<Boolean> validatePostLoginReq(PostLoginReq postLoginReq) {
        if (postLoginReq.getUsername() == null) {
            throw new BaseException(USERS_EMPTY_USERID);
        }

        if (postLoginReq.getPassword() == null) {
            throw new BaseException(USERS_EMPTY_PASSWORD);
        }

        //아이디 유효성 검증
        // 아이디 유효성 검증 (영문 소문자로 시작, 영문 소문자와 숫자의 조합, 총 5~12자)
        if(!isRegexUsername(postLoginReq.getUsername())) {
            throw new BaseException(USERS_INVALID_USERNAME);
        }

        //패스워드 유효성 검증
        //(8-20자, 영문 대/소문자, 숫자, 특수문자 조합)
        if(!isRegexPassword(postLoginReq.getPassword())) {
            throw new BaseException(USERS_INVALID_PASSWORD);
        }

        return null; // 유효성 검사 통과
    }
}
