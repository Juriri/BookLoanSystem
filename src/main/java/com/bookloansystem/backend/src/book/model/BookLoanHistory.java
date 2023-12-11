package com.bookloansystem.backend.src.book.model;

import static com.bookloansystem.backend.common.Constant.State.*;
import com.bookloansystem.backend.common.utils.UUIDGenerator;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@Data
public class BookLoanHistory {
    private String username;
    private String bookId;
    private Date loanDate;
    private Date returnDate;
    private String status;
    private String bookTitle;

    @Builder
    public BookLoanHistory(String username, Book book) {
        this.username = username;
        this.bookId = book.getBookId();
        this.bookTitle = book.getBookTitle();
        this.loanDate = Date.valueOf(LocalDate.now());

        // 현재 날짜에서 2주를 더한 날짜 계산
        LocalDate returnLocalDate = LocalDate.now().plus(2, ChronoUnit.WEEKS);
        this.returnDate = Date.valueOf(returnLocalDate);

        this.status = String.valueOf(ON_LOAN);
    }
}
