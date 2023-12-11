package com.bookloansystem.backend.src.book.dto;

import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String createdAt;
    private int quantity;
    private String bookContent;
    private String bookThumbnail;

    public Book toEntity() {
        return Book.builder()
                .bookCategory(bookCategory)
                .bookTitle(bookTitle)
                .author(author)
                .publisher(publisher)
                .publicationDate(publicationDate)
                .quantity(quantity)
                .bookContent(bookContent)
                .bookThumbnail(bookThumbnail)
                .build();
    }
}
