package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.src.book.dto.PostBookLoanRes;
import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.book.model.BookLoanHistory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BookMapperRepository {
    // 도서 등록
    void bookInsert(Book book);

    // 카테고리 조회
    List<String> findCategory();

    // 모든 도서 목록 조회
    List<Book> findAllBooks();

    // 고유 Id 도서 찾기
    Optional<Book> findBookById(String bookId);

    // 카테고리, 책 제목, 출판사, 출판일  도서 찾기
    Optional<Book> findBookByCategoryAndTitleAndAuthorAndPublisher(String bookCategory, String bookTitle, String author
    , String publisher);

    // 저장된 도서 정보 수정
    void bookSave(Book book);



    // 도서 대출 등록
    void bookloanInsert(BookLoanHistory bookLoanHistory);

    // 동일 bookId 대출 이력 확인
    List<BookLoanHistory> findbookloanList(String bookId);

    // username, bookId로 대출 이력 확인
    Optional<BookLoanHistory> findbookloan(String username, String bookId);

    // 반납
    void bookloanDelete(BookLoanHistory bookLoanHistory);
}
