package com.example.book.controller;

import com.example.book.entity.Likes;
import com.example.book.entity.Record;
import com.example.book.service.LikesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class LikesControllerTest {
    @Autowired
    private LikesService likesService;

    @Autowired
    private MockMvc mockMvc;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTczNjY2NDQsImV4cCI6MTY1NzM3MDI0NH0.spbrzZIXc5b8FqfFOtTUYGiIcOkyuBUQi_zLEDVFTOw";

    @Test
    @Transactional
    @Rollback(value = false)
    public void save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Likes likes = new Likes();
        likes.setTitle("달러구트 꿈 백화점 (주문하신 꿈은 매진입니다)");
        likes.setAuthor("이미예");
        likes.setImage("https://bookthumb-phinf.pstatic.net/cover/164/054/16405427.jpg?type=m1&udate=20210813");
        likes.setPublisher("팩토리나인이미예");
        likes.setCategory("소설");

        mockMvc.perform(post("/api/v1/like")
                .header("X-AUTH-TOKEN", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(likes)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findAll() throws Exception{

        mockMvc.perform(get("/api/v1/likes")
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void deleteLike() throws Exception {

        mockMvc.perform(delete("/api/v1/like/" + "1")
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
