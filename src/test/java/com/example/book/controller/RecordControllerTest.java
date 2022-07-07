package com.example.book.controller;

import com.example.book.entity.Record;
import com.example.book.entity.User;
import com.example.book.service.RecordService;
import com.example.book.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class RecordControllerTest {
    @Autowired
    private RecordService recordService;

    @Autowired
    private MockMvc mockMvc;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTcwMjE4NjIsImV4cCI6MTY1NzAyNTQ2Mn0.O8tkGQTV0rzps2OvS81rgQuuUE7vQuQaFkWemGj7nYM";
    String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTcwNzM5NzMsImV4cCI6MTY1NzMzMzE3M30.7_Rfeibnr1jpQJOxCOy5kfZOj4-pibRdWRrlYIjBL-4";


    @Test
    @Transactional
    @Rollback(value = false)
    public void save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Record record = new Record();
        record.setTitle("달러구트 꿈 백화점 (주문하신 꿈은 매진입니다)");
        record.setAuthor("이미예");
        record.setImage("https://bookthumb-phinf.pstatic.net/cover/164/054/16405427.jpg?type=m1&udate=20210813");
        record.setPublisher("팩토리나인이미예");
        record.setMemo("good");
        record.setDate("2022-07-07");
        record.setRating(5.0);
        record.setCategory("소설");


        mockMvc.perform(post("/api/v1/record")
                .header("X-AUTH-TOKEN", token)
//                .header("REFRESH-TOKEN", refreshToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findList() throws Exception {
        mockMvc.perform(
                get("/api/v1/record-list/" + "2022-07-02")
                .header("X-AUTH-TOKEN", token)
                .header("REFRESH-TOKEN", refreshToken))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findOne() throws Exception {
        mockMvc.perform(
                get("/api/v1/record-detail/" + "7")
                    .header("X-AUTH-TOKEN", token)
                    .header("REFRESH-TOKEN", refreshToken)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void update() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Record record = new Record();
        record.setTitle("달러구트 꿈 백화점 (주문하신 꿈은 매진입니다)");
        record.setAuthor("이미예");
        record.setImage("https://bookthumb-phinf.pstatic.net/cover/164/054/16405427.jpg?type=m1&udate=20210813");
        record.setPublisher("팩토리나인이미예");
        record.setMemo("좋아요!!");
        record.setDate("2022-07-02");
        record.setRating(5.0);
        record.setCategory("소설");

        mockMvc.perform(
                put("/api/v1/record/" + "7")
                    .header("X-AUTH-TOKEN", token)
                    .header("REFRESH-TOKEN", refreshToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(record))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void deleteRecord() throws Exception {
         mockMvc.perform(
                    delete("/api/v1/record/" + "7")
                        .header("X-AUTH-TOKEN", token)
                        .header("REFRESH-TOKEN", refreshToken)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}
