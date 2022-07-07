package com.example.book.controller;

import com.example.book.service.AuthService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Test
    public void refreshToken() throws Exception {
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTcxNjAzOTUsImV4cCI6MTY1NzQxOTU5NX0.4ofg0iixiM-nXMqzF67L3b1T9cxbbpazH1nmZmvENc0";
        mockMvc.perform(post("/api/v1/refresh")
//                .header("X-AUTH-TOKEN", token)
                .header("REFRESH-TOKEN", refreshToken))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
