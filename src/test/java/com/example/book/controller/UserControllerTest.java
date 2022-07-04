package com.example.book.controller;

import com.example.book.entity.User;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional // 트랜잭션을 자동으로 수행, 예외나 에러가 발생하면 test 중 db에 입력된 값들을 rollback 해준다.
    @Rollback(value = false) // 테스트 중 db에 입력된 값을 테스트 전 원래상태로 rollback 해준다. default는 true
    public void signUp() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        // 아이디와 이메일이 중복될 경우 저장 안됨.
        User user = new User();
        user.setId("test1");
        user.setPassword("111");
        user.setEmail("eeee1");
        user.setProfileImage(null);

        mockMvc.perform(post("/api/v1/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void login() throws Exception{
        mockMvc.perform(post("/api/v1/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"test12\", \"password\": \"111\"}"))
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    public void findAll() throws Exception{
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findOne() throws Exception{
        mockMvc.perform(get("/api/v1/user/"+"test1"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}