package com.example.book.service;

import com.example.book.entity.User;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // 의존성 주입
public class AuthService {

    private final UserRepository userRepository;

    /**
     * 회원정보 추가(회원가입)
     * @param user 회원 가입 폼으로부터 전달받은 정보
     * @return 이미 저장된 email 여부에 따라 사용자 추가가 되었는지에 대한 boolean 값.
     */
    public boolean save(User user){
        // 이미 저장된 email이 있을 경우
        if(userRepository.findUserByEmail(user.getEmail()) != null)
            return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 회원 비밀번호 암호화
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

}
