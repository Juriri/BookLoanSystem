package com.bookloansystem.backend.src.book.model;

import com.bookloansystem.backend.common.Constant;
import com.bookloansystem.backend.common.utils.UUIDGenerator;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Getter
@NoArgsConstructor
@Data
public class Book {
    private String bookId;
    private String bookCategory;
    private String bookTitle;
    private String author;
    private String publisher;
    private Date publicationDate;
    private String createdAt;
    private int quantity;
    private String bookContent;
    private String bookThumbnail;

    @Builder
    public Book(String bookCategory, String bookTitle, String author, String publisher,
                Date publicationDate, String createdAt, int quantity, String bookContent, String bookThumbnail) {
        this.bookId = UUIDGenerator.generateUUID();
        this.bookCategory = bookCategory;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.createdAt = createdAt;
        this.quantity = quantity;
        this.bookContent = bookContent;
        this.bookThumbnail = bookThumbnail;
    }
}
