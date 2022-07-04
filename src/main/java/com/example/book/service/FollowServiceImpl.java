package com.example.book.service;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.Follow;
import com.example.book.entity.User;
import com.example.book.repository.FollowRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService{

    private final FollowRepository followRepository;
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
    public List<User> findFollower(String token) {
        User user = getUserId(token);

        List<Follow> followerList = followRepository.findFollowByToUser(user.getId());

        List<User> followerUserList = new ArrayList<>();
        for(int i = 0; i < followerList.size(); i++){
            followerUserList.add(followerList.get(i).getFromUser());
        }

        return followerUserList;
    }

    @Override
    public List<User> findFollowing(String token) {
        User user = getUserId(token);

        List<Follow> followingList = followRepository.findFollowByFromUser(user.getId());

        List<User> followingUserList = new ArrayList<>();
        for(int i = 0; i < followingList.size(); i++){
            followingUserList.add(followingList.get(i).getFromUser());
        }

        return followingUserList;
    }

    @Override
    public boolean save(String fromUser, String toUserId) {
        User user = getUserId(fromUser);
        User toUser = userRepository.findUserById(toUserId);

        if(user != null && toUser != null) {
            Follow follow = new Follow();
            follow.setFromUser(user);
            follow.setToUser(toUser);
            followRepository.save(follow);

            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String token, Long id) {
        User user = getUserId(token);

        Follow follow = new Follow();
        follow.setId(id);

        if(user != null) {
            followRepository.delete(follow);

            return true;
        }
        return false;
    }
}
