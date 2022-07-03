package com.example.book.service;

import com.example.book.entity.Likes;

import java.util.List;

public interface LikesService {

    List<Likes> findAll(String accessToken);

    boolean save(String token, Likes likes);

    boolean delete(String accessToken, Long num);
}
