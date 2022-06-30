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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @Transactional
    @Rollback(value = true)
    public void save() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Record record = new Record();
        record.setTitle("제목");
        record.setAuthor("저자");
        record.setImage("이미지");
        record.setMemo("내용");
        record.setDate("날짜");
        record.setPublisher("출판사");
        record.setRating(5.0);
        record.setCategory("카테고리");

        mockMvc.perform(post("/api/v1/record")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(record)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
