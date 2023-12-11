package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponse;
import com.bookloansystem.backend.src.book.dto.PostBookLoanRes;
import com.bookloansystem.backend.src.book.dto.PostBookReq;
import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.user.UserService;
import com.bookloansystem.backend.src.user.dto.PostLoginReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;

import java.text.ParseException;
import java.util.List;

import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;
import static com.bookloansystem.backend.common.utils.ValidationRegex.*;


@Slf4j
@RequiredArgsConstructor
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    /**
     * 도서 등록 API
     * [POST] /registerBook
     * @param bookCategory 도서 카테고리 (FK)
     * @param bookTitle 도서 제목
     * @param author 도서 저자
     * @param publisher 출판사
     * @param publicationDate 도서 출판일 (yyyy-MM-dd)
     * @param quantity 도서 수량
     * @param bookContent 도서 소개 내용
     * @param bookThumbnail 도서 썸네일
     * @return BaseResponse<String>
     */

    @ResponseBody
    @ApiOperation(value = "도서 등록")
    @GetMapping("/registerBook")
    public BaseResponse<String> createBook(
            @RequestParam(name = "bookCategory (인문/예술/에세이/공학)") String bookCategory,
            @RequestParam(name = "bookTitle") String bookTitle,
            @RequestParam(name = "author") String author,
            @RequestParam(name = "publisher") String publisher,
            @RequestParam(name = "publicationDate (yyyy-mm-dd)") String publicationDate,
            @RequestParam(name = "quantity") Integer quantity,
            @RequestParam(name = "bookContent", required = false) String bookContent,
            @RequestParam(name = "bookThumbnail", required = false) String bookThumbnail) throws ParseException {

        PostBookReq postBookReq = new PostBookReq(bookCategory, bookTitle, author, publisher, publicationDate,
                quantity, bookContent, bookThumbnail);
        if (validatePostBookReq(postBookReq)!= null) {
            return validatePostBookReq(postBookReq);
        } else {
            return new BaseResponse<>(bookService.registerBook(postBookReq));
        }
    }


    //도서 등록 정보 공란과 유효성 검증
    private BaseResponse<String> validatePostBookReq(PostBookReq postBookReq) throws ParseException {

        String bookCategory = postBookReq.getBookCategory();
        String bookTitile = postBookReq.getBookTitle();
        String author = postBookReq.getAuthor();
        String publisher = postBookReq.getPublisher();
        String publicationDate = postBookReq.getPublicationDate();
        int quantity = postBookReq.getQuantity();
        /*String bookContent = postBookReq.getBookContent();
        String bookThumbnail = postBookReq.getBookThumbnail();*/

        // String 공란일 때 예외 처리
        if (bookCategory.isEmpty() || bookTitile.isEmpty() || author.isEmpty()||
        publisher.isEmpty() || publicationDate.isEmpty()) {
            throw new BaseException(BOOK_EMPTY_INFO);
        }

        // 최소 최대 글자 확인
        if (!isRegexBook(bookCategory, 1, 20) || !isRegexBook(bookTitile,1,100) || !isRegexBook(author,1,20)
        || !isRegexBook(publisher,1,20)) {
            throw new BaseException(BOOK_INVALID_REGEX);
        }

        // 숫자가 아닐 때 예외처리
        if(quantity <= 0 || !NumberUtils.isDigits(String.valueOf(quantity))){
            throw new BaseException(BOOK_INVALID_QUANTITY);
        }

        // 이미지 주소 url이 아닐 때 예외 처리
        /*if(bookThumbnail) {

        }*/
        return null; // 유효성 검사 통과
    }


    /**
     * 도서 정보 수정 API
     * [POST] /editBook
     * @param bookId 도서 고유 아이디
     * @param bookCategory 도서 카테고리 (FK)
     * @param bookTitle 도서 제목
     * @param author 도서 저자
     * @param publisher 출판사
     * @param publicationDate 도서 출판일 (yyyy-MM-dd)
     * @param quantity 도서 수량
     * @param bookContent 도서 소개 내용
     * @param bookThumbnail 도서 썸네일
     * @return BaseResponse<String>
     */

    @ResponseBody
    @ApiOperation(value = "도서 수정")
    @GetMapping("/editBook")
    public BaseResponse<String> editBook(
            @RequestParam(name = "bookId") String bookId,
            @RequestParam(name = "bookCategory (인문/예술/에세이/공학)", required = false) String bookCategory,
            @RequestParam(name = "bookTitle", required = false) String bookTitle,
            @RequestParam(name = "author", required = false) String author,
            @RequestParam(name = "publisher", required = false) String publisher,
            @RequestParam(name = "publicationDate (yyyy-mm-dd)", required = false) String publicationDate,
            @RequestParam(name = "quantity", required = false) Integer quantity,
            @RequestParam(name = "bookContent", required = false) String bookContent,
            @RequestParam(name = "bookThumbnail", required = false) String bookThumbnail) throws ParseException {

        int parsedQuantity = (quantity == null) ? 0 : quantity;

        PostBookReq postBookReq = new PostBookReq(bookCategory, bookTitle, author, publisher, publicationDate,
                parsedQuantity, bookContent, bookThumbnail);
        return new BaseResponse<>(bookService.editBook(bookId, postBookReq));
    }


    /**
     * 도서 목록 호출 API
     * [GET] /findAllBooks
     */

    @ResponseBody
    @ApiOperation(value = "도서 목록 호출")
    @GetMapping("/findAllBooks")
    public BaseResponse<List<Book>> findAllBook() {
        return new BaseResponse<>(bookService.findAllBook());
    }


    /**
     * 도서 대출 입력 API
     * [POST] /loanInsert
     * @param bookId 도서 고유 아이디
     * @param postLoginReq 로그인 모델
     */

    @ResponseBody
    @ApiOperation(value = "도서 대출 실행")
    @PostMapping("/loanInsert")
    public BaseResponse<String> createLoan(@RequestBody PostLoginReq postLoginReq, @RequestParam String bookId) {
        if (userService.loginUser(postLoginReq)) {
            return new BaseResponse<>(bookService.createLoan(postLoginReq.getUsername(), bookId));
        } else {
            throw new BaseException(USERS_NOT_EXISTS_PASSWORD);
        }
    }

    /**
     * 도서 대출 이력 확인 API
     * [GET] /loanFind
     * @param bookId 도서 고유 아이디
     */

    @ResponseBody
    @ApiOperation(value = "도서 대출 이력 확인")
    @PostMapping("/loanFind")
    public BaseResponse<List<PostBookLoanRes>> findLoan(@RequestParam String bookId){
        return new BaseResponse<>(bookService.findLoan(bookId));
    }


    /**
     * 도서 대출 반납 API
     * [POST] /loanInsert
     * @param bookId 도서 고유 아이디
     * @param postLoginReq 로그인 모델
     */

    @ResponseBody
    @ApiOperation(value = "도서 반납 실행")
    @PostMapping("/loanReturn")
    public BaseResponse<String> returnLoan(@RequestBody PostLoginReq postLoginReq, @RequestParam String bookId) {
        if (userService.loginUser(postLoginReq)) {
            return new BaseResponse<>(bookService.createLoan(postLoginReq.getUsername(), bookId));
        } else {
            throw new BaseException(USERS_NOT_EXISTS_PASSWORD);
        }
    }
}
