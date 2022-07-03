package com.example.book.service;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.Likes;
import com.example.book.entity.User;
import com.example.book.repository.LikesRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService{

    private final LikesRepository likesRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // 임시 메소드
    public User getUserId(String token) {
        // 토큰에서 회원 id 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        // 가져온 회원 아이디 기반으로 회원 데이터 조회
        User user = userRepository.findUserById(authentication.getName());

        return user;
    }


    @Override
    public List<Likes> findAll(String accessToken) {
        User user = getUserId(accessToken);

        if(user != null){
            return likesRepository.findByUserId(user.getId());
        }
        return null;
    }

    @Override
    public boolean save(String token, Likes likes) {
        User user = getUserId(token);

        if(user != null){
            likes.setUser(user);
            likesRepository.save(likes);
            return true;
        }

        return false;
    }

    @Override
    public boolean delete(String accessToken, Long num) {
        User user = getUserId(accessToken);

        Likes likes = new Likes();
        likes.setId(num);

        if(user != null){
            likesRepository.delete(likes);
            return true;
        }
        return false;
    }
}
