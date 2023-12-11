package com.bookloansystem.backend.src.book.dto;

import com.bookloansystem.backend.common.exceptions.BaseException;
import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;

import com.bookloansystem.backend.src.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostBookReq {
    private String bookCategory;
    private String bookTitle;
    private String author;
    private String publisher;
    private String publicationDate;
    private int quantity;
    private String bookContent;
    private String bookThumbnail;

    public Book toEntity() {
        return Book.builder()
                .bookCategory(bookCategory)
                .bookTitle(bookTitle)
                .author(author)
                .publisher(publisher)
                .publicationDate(convertStringToDate(publicationDate))
                .quantity(quantity)
                .bookContent(bookContent)
                .bookThumbnail(bookThumbnail)
                .build();
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
}
