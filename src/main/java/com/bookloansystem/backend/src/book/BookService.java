package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponseStatus;
import com.bookloansystem.backend.src.book.dto.PostBookLoanRes;
import com.bookloansystem.backend.src.book.dto.PostBookReq;
import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.book.model.BookLoanHistory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookMapperRepository bookMapperRepository;


    @Transactional
    public String registerBook(PostBookReq postBookReq) {
        try {
            // book 객체를 DB에 저장
            bookMapperRepository.bookInsert(postBookReq.toEntity());

        } catch (DataIntegrityViolationException e) {
            // 참조되는 카테고리 이외 입력 시 예외처리
            log.info("category = {}", bookMapperRepository.findCategory());
            throw new BaseException(BaseResponseStatus.BOOK_INVALID_CATEGORY);
        }

        return "도서 등록이 완료되었습니다.";
    }


    @Transactional (readOnly = true)
    public List<Book> findAllBook() {
        try {
            // book 객체 불러오기
            return bookMapperRepository.findAllBooks();

        } catch (DataIntegrityViolationException e) {
            // 참조되는 카테고리 이외 입력 시 예외처리
            log.info("category = {}", bookMapperRepository.findCategory());
            throw new BaseException(BaseResponseStatus.BOOK_INVALID_CATEGORY);
        }
    }


    @Transactional
    public String editBook(String bookId, PostBookReq postBookReq) throws ParseException {
        // book 객체 찾기
        Optional<Book> existingBook = bookMapperRepository.findBookById(bookId);

        if (existingBook.isEmpty()) {
            throw new BaseException(BaseResponseStatus.BOOK_INVALID_ID);
        } else {
            Book book = existingBook.get();
            String bookCategory = postBookReq.getBookCategory();
            if (bookCategory != null) {
                book.setBookCategory(bookCategory);
            }

            String bookTitle = postBookReq.getBookTitle();
            if (bookTitle != null) {
                book.setBookTitle(bookTitle);
            }

            String author = postBookReq.getAuthor();
            if (author != null) {
                book.setAuthor(author);
            }

            String publisher = postBookReq.getPublisher();
            if (publisher != null) {
                book.setPublisher(publisher);
            }

            String publicationDate = postBookReq.getPublicationDate();
            if (publicationDate != null) {
                book.setPublicationDate(convertStringToDate(publicationDate));
            }

            int quantity = postBookReq.getQuantity();
            if (quantity > 0 && NumberUtils.isDigits(String.valueOf(quantity))) {
                book.setQuantity(quantity);
            }

            bookMapperRepository.bookSave(book);
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

    public String createLoan(String username, String bookId) {
        // bookId로 책 조회
        Optional<Book> existingBook = bookMapperRepository.findBookById(bookId);

        if (existingBook.isEmpty()) {
            throw new BaseException(BOOK_INVALID_ID);
        } else if (existingBook.get().getQuantity() <= 0) {
            throw new BaseException(BOOK_LESS_QUANTITY);
        }
        else {
            BookLoanHistory bookLoanHistory = new BookLoanHistory(username, existingBook.get());

            bookMapperRepository.bookloanInsert(bookLoanHistory);

            int preQuantity = existingBook.get().getQuantity();
            existingBook.get().setQuantity(preQuantity-1);
            return "대출이 완료 되었습니다. 반납일: "+bookLoanHistory.getReturnDate();
        }
    }


    public List<PostBookLoanRes> findLoan(String bookId) {
        // bookId로 책 조회
        Optional<Book> existingBook = bookMapperRepository.findBookById(bookId);

        if (existingBook.isEmpty()) {
            throw new BaseException(BOOK_INVALID_ID);
        } else {
            return bookMapperRepository.bookloanList(bookId);
        }
    }
}
