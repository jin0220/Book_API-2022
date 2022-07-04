package com.example.book.service;

import com.example.book.entity.Follow;
import com.example.book.entity.User;

import java.util.List;

public interface FollowService {
    // 사용자의 팔로워 리스트 조회
    List<User> findFollower(String token);

    // 사용자의 팔로잉 리스트 조회
    List<User> findFollowing(String token);

    // 팔로우하기
    boolean save(String fromUser, String toUser);

    //팔로우 취소하기
    boolean delete(String token, Long id);
}
