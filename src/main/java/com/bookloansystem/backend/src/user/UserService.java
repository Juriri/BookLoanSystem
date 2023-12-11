package com.bookloansystem.backend.src.user;

import com.bookloansystem.backend.common.exceptions.BaseException;
import com.bookloansystem.backend.common.response.BaseResponseStatus;
import com.bookloansystem.backend.src.user.dto.PostLoginReq;
import com.bookloansystem.backend.src.user.dto.PostUserReq;
import com.bookloansystem.backend.src.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import static com.bookloansystem.backend.common.response.BaseResponseStatus.*;


@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapperRepository userMapperRepository;
    @Transactional
    public String createUser(PostUserReq postUserReq) {
        //회원 ID 중복 체크
        Optional<User> checkUser = userMapperRepository.findUserByUsername(postUserReq.getUsername());
        if(checkUser.isPresent()){
            throw new BaseException(POST_USERS_EXISTS_USERID);
        }

        //회원 이메일 중복 체크
        checkUser = userMapperRepository.findUserByEmail(postUserReq.getEmail());
        if(checkUser.isPresent()) {
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
        }


        // 입력한 패스워드 암호화 (BCryptPasswordEncoder 사용)
        String encryptPwd;
        try {
            encryptPwd = new BCryptPasswordEncoder().encode(postUserReq.getPassword());
            postUserReq.setPassword(encryptPwd);
        } catch (Exception exception) {
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }

        // user 객체를 DB에 저장
        userMapperRepository.userInsert(postUserReq.toEntity());

        return postUserReq.getName()+ "님 환영합니다.";

    }

    @Transactional (readOnly = true)
    public boolean loginUser(PostLoginReq postLoginReq) {
        //회원 ID 체크
        Optional<User> checkUser = userMapperRepository.findUserByUsername(postLoginReq.getUsername());
        if(checkUser.isEmpty()){
            throw new BaseException(USERS_NOT_EXISTS_USERNAME);
        }

        // DB에 저장된 유저의 패스워드
        String userPwd = checkUser.get().getPassword();

        // 입력한 패스워드
        String inputPwd = postLoginReq.getPassword();

        if (new BCryptPasswordEncoder().matches(inputPwd, userPwd)){
            // 비밀번호가 일치하면 true 반환.
            return true;
        } else {
            // 비밀번호가 일치하지 않으면 로그인 실패 예외를 던짐.
            throw new BaseException(USERS_NOT_EXISTS_PASSWORD);
        }
    }
}
