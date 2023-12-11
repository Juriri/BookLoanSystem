package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponse;
import com.bookloansystem.backend.src.book.dto.PostBookReq;
import com.bookloansystem.backend.src.user.dto.PostUserReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;
import static com.bookloansystem.backend.common.utils.ValidationRegex.*;
import static com.bookloansystem.backend.common.utils.ValidationRegex.isRegexName;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    /**
     * 도서 등록 API
     * [POST] /register
     * @param postBookReq 도서 등록 모델
     * @return BaseResponse<String>
     */

    @ResponseBody
    @PostMapping("/register")
    public BaseResponse<String> createUser(@RequestBody PostBookReq postBookReq) {
        if (validatePostUserReq(postBookReq)!= null) {
            return validatePostUserReq(postBookReq);
        } else {
            return new BaseResponse<>(bookService.registerBook(postBookReq));
        }
    }

    //도서 등록 정보 공란과 유효성 검증
    private BaseResponse<String> validatePostUserReq(PostBookReq postBookReq) {

        return null; // 유효성 검사 통과
    }


}
