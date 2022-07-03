package com.example.book.repository;

import com.example.book.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {
    List<Likes> findByUserId(@Param("userId") String userId);
}
