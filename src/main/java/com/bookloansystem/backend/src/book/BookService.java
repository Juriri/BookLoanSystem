package com.bookloansystem.backend.src.book;

import com.bookloansystem.backend.common.response.BaseResponseStatus;
import com.bookloansystem.backend.src.book.dto.PostBookReq;
import com.bookloansystem.backend.src.user.dto.PostUserReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class BookService {
    @Autowired
    private BookMapperRepository bookMapperRepository;


    @Transactional
    public String registerBook(PostBookReq postBookReq) {
        // user 객체를 DB에 저장
        bookMapperRepository.bookInsert(postBookReq.toEntity());

        return "도서 등록이 완료되었습니다.";
    }
}
