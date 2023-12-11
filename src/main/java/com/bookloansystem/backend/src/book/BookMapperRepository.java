package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.src.book.model.Book;
import com.bookloansystem.backend.src.user.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface BookMapperRepository {
    // 도서 등록
    void bookInsert(Book book);

}
