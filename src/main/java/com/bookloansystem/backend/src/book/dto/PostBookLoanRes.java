package com.bookloansystem.backend.src.book.dto;

import com.bookloansystem.backend.src.book.model.BookLoanHistory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBookLoanRes {
    private String username;
    private String bookTitle;
    private Date loanDate;
    private Date returnDate;
    private String status;

    public PostBookLoanRes(BookLoanHistory bookLoanHistory) {
        this.username = bookLoanHistory.getUsername();
        this.bookTitle = bookLoanHistory.getBookTitle();

        this.loanDate = bookLoanHistory.getLoanDate();
        this.returnDate = bookLoanHistory.getReturnDate();
        this.status = bookLoanHistory.getStatus();
    }
}
