package com.example.book.repository;

import com.example.book.entity.Follow;
import com.example.book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    // 팔로워 조회
    List<Follow> findFollowByToUser(@Param("toUser") User toUser);

    // 팔로잉 조회
//    @Query(value = "select * from User where fromUser = :fromUser")
    List<Follow> findFollowByFromUser(@Param("fromUser") User fromUser);
}
