package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponse;
import com.bookloansystem.backend.common.response.BaseResponseStatus;
import com.bookloansystem.backend.src.book.dto.PostBookLoanRes;
import com.bookloansystem.backend.src.book.dto.PostBookReq;
import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.book.model.BookLoanHistory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;
import static com.bookloansystem.backend.common.utils.ValidationRegex.isRegexBook;
import static com.bookloansystem.backend.common.utils.ValidationRegex.isValidDate;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookMapperRepository bookMapperRepository;

    // common:: bookId로 book 객체 존재 확인
    private Optional<Book> getExistingBook(String bookId) {
        return bookMapperRepository.findBookById(bookId);
    }

    // common:: username, bookId로 bookloanhistory 객체 존재 확인
    private Optional<BookLoanHistory> getExistingBookLoanHistory(String username, String bookId) {
        return bookMapperRepository.findbookloan(username, bookId);
    }

    // common:: bookId로 book 객체 조회
    public Book findBookById(String bookId) {
        // bookId로 책 조회
        return getExistingBook(bookId).orElseThrow(()
                -> new BaseException(BOOK_INVALID_ID));
    }

    // common:: bookId로 조회 이력 확인
    public List<BookLoanHistory> findBookLoanHistoryListByBookId(String bookId) {
        // bookId로 대출 이력 조회
        List<BookLoanHistory> bookloanList = bookMapperRepository.findbookloanList(bookId);

        if (bookloanList.isEmpty()) {
            throw new BaseException(BOOK_LOAN_NOT_EXISTS);
        } else {
            return bookloanList;
        }
    }

    // common:: username, bookId로 대출 이력 확인
    public BookLoanHistory findBookLoanHistoryByUsernameAndBookId(String username, String bookId){
        // username, bookId로 책 대출 이력 조회
        return getExistingBookLoanHistory(username, bookId).orElseThrow(()
                -> new BaseException(BOOK_LOAN_NOT_EXISTS));
    }

    // common:: 도서 보유 수량 제어
    private void updatedBookQuantity(Book book, String op, int num) {
        int preQuantity = book.getQuantity();

        switch (op) {
            case "+":
                book.setQuantity(preQuantity + num);
                break;
            case "-":
                if (preQuantity - num < 0) {
                    throw new BaseException(BOOK_LESS_QUANTITY);
                }
                book.setQuantity(preQuantity - num);
                break;
            default:
                throw new BaseException(BOOK_EMPTY_INFO);
        }

        // DB book 객체의 수량 업데이트
        bookMapperRepository.bookSave(book);
    }

    @Transactional
    public String registerBook(PostBookReq postBookReq) {
        try {
            log.info("postBookReq = {}", postBookReq.toString());
            // book 객체 존재 확인
            Optional<Book> existbook = bookMapperRepository.findBookByCategoryAndTitleAndAuthorAndPublisher
                    (postBookReq.getBookCategory(), postBookReq.getBookTitle(), postBookReq.getAuthor(), postBookReq.getPublisher());

            log.info("existbook = {}", existbook);
            if (existbook.isPresent()) {
                throw new BaseException(POST_BOOK_EXISTS);
            } else {
                // book 객체를 DB에 저장
                bookMapperRepository.bookInsert(postBookReq.toEntity());
            }
        } catch (DataIntegrityViolationException e) {
            // 참조되는 카테고리 이외 입력 시 예외처리
            throw new BaseException(BaseResponseStatus.BOOK_INVALID_CATEGORY);
        }

        return "도서 등록이 완료 되었습니다.";
    }


    @Transactional (readOnly = true)
    public List<Book> findAllBook() {
        // book 모든 객체 불러오기
        return bookMapperRepository.findAllBooks();
    }


    @Transactional
    public String editBook(String bookId, PostBookReq postBookReq) throws ParseException {
        // book 객체 찾기
        Optional<Book> existingBook = getExistingBook(bookId);

        if (existingBook.isEmpty()) {
            throw new BaseException(BaseResponseStatus.BOOK_INVALID_ID);
        } else {
            try {

                Book book = existingBook.get();
                String bookCategory = postBookReq.getBookCategory();
                if (bookCategory != null) {
                    if (isRegexBook(bookCategory, 1, 20)) {
                        book.setBookCategory(bookCategory);
                    } else {
                        throw new BaseException(BOOK_INVALID_REGEX);
                    }
                }

                String bookTitle = postBookReq.getBookTitle();
                if (bookTitle != null) {
                    if (isRegexBook(bookTitle,1,100)) {
                        book.setBookTitle(bookTitle);
                    } else {
                        throw new BaseException(BOOK_INVALID_REGEX);
                    }
                }

                String author = postBookReq.getAuthor();
                if (author != null) {
                    if(isRegexBook(author,1,20)) {
                        book.setAuthor(author);
                    } else {
                        throw new BaseException(BOOK_INVALID_REGEX);
                    }
                }

                String publisher = postBookReq.getPublisher();
                if (publisher != null) {
                    if(isRegexBook(publisher,1,20)) {
                        book.setPublisher(publisher);
                    } else {
                        throw new BaseException(BOOK_INVALID_REGEX);
                    }
                }

                String publicationDate = postBookReq.getPublicationDate();
                if (publicationDate != null) {
                    if (isValidDate(publicationDate)) {
                        book.setPublicationDate(convertStringToDate(publicationDate));
                    } else {
                        throw new BaseException(BOOK_INVALID_DATE);
                    }
                }

                int quantity = postBookReq.getQuantity();
                if (quantity > 0 && NumberUtils.isDigits(String.valueOf(quantity))) {
                    book.setQuantity(quantity);
                }

                bookMapperRepository.bookSave(book);
            } catch (DataIntegrityViolationException e) {
                // 참조되는 카테고리 이외 입력 시 예외처리
                throw new BaseException(BaseResponseStatus.BOOK_INVALID_CATEGORY);
            }
        }

        return "도서 정보가 수정 되었습니다.";
    }

    private Date convertStringToDate(String dateString) {
        try {
            // 문자열을 java.util.Date로 파싱
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = dateFormat.parse(dateString);

            // java.util.Date를 java.sql.Date로 변환
            return new Date(utilDate.getTime());
        } catch (ParseException e) {
            throw new BaseException(BOOK_INVALID_DATE);
        }
    }

    // 대출 이력 생성
    @Transactional
    public String createLoan(String username, String bookId) {
        // bookId로 책 조회
        Book book = findBookById(bookId);

        // 동일 bookId 대출 이력 확인
        Optional<BookLoanHistory> existingBookLoanHistory = getExistingBookLoanHistory(username, bookId);

        // 기대출 예외처리
        if (existingBookLoanHistory.isPresent()) {
            throw new BaseException(POST_BOOK_LOAN_EXISTS);
        } else {
            // 대출 시 보유 수량 -1 실행
            updatedBookQuantity(book, "-", 1);

            // 대출 이력 객체 생성
            BookLoanHistory bookLoanHistory = new BookLoanHistory(username, book);

            // DB 대출 테이블에 객체 저장
            bookMapperRepository.bookloanInsert(bookLoanHistory);

            return "대출이 완료 되었습니다. 반납일: "+bookLoanHistory.getReturnDate();
        }
    }


    // bookId로 대출 이력 찾기
    @Transactional (readOnly = true)
    public List<PostBookLoanRes> findLoan(String bookId) {
        // bookId로 책 대출 이력 조회
        List<BookLoanHistory> bookLoanHistoryList = findBookLoanHistoryListByBookId(bookId);

        // 결과 반환 모델 선언
        List<PostBookLoanRes> postBookLoanRes = new ArrayList<>();

        for(BookLoanHistory bookLoanHistory : bookLoanHistoryList) {
            postBookLoanRes.add(new PostBookLoanRes(bookLoanHistory));
        }

        return postBookLoanRes;
    }


    // 반납 처리
    @Transactional
    public String deleteLoan(String username, String bookId) {
        // bookId로 book 객체 조회
        Book book = findBookById(bookId);

        // username, bookId로 대출 이력 조회
        BookLoanHistory bookLoanHistory = findBookLoanHistoryByUsernameAndBookId(username, bookId);

        // DB 대출 테이블에 객체 삭제
        bookMapperRepository.bookloanDelete(bookLoanHistory);

        // 보유 수량 +1 실행
        updatedBookQuantity(book, "+", 1);

        return "반납이 완료 되었습니다.";
    }
}
