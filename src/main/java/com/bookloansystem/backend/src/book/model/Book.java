package com.bookloansystem.backend.src.book.model;

import com.bookloansystem.backend.common.Constant;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Data
public class Book {
    private String bookId;
    private String bookCategory;
    private String bookTitle;
    private String author;
    private String publisher;
    private String publicationDate;
    private String createdAt;
    private int quantity;
    private String bookContent;
    private String bookThumbnail;

    @Builder
    public Book(String bookCategory, String bookTitle, String author, String publisher,
                String publicationDate, String createdAt, int quantity, String bookContent, String bookThumbnail) {
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
