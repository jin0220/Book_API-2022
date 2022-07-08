package com.example.book.controller;

import com.example.book.service.FollowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.ir.annotations.Ignore;
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
public class FollowControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FollowService followService;
    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTcyNTU3NjEsImV4cCI6MTY1NzI1OTM2MX0.itnzGt4WR953qE25G4RFNlZwEWFvbq8fHkfLh2Znf6s";
    @Test
    @Transactional
    @Rollback(value = false)
    public void save() throws Exception {

        mockMvc.perform(post("/api/v1/follow")
                .header("X-AUTH-TOKEN", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"test1\"}"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findFollowers() throws Exception {

//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MTIiLCJpYXQiOjE2NTcxNzkzOTMsImV4cCI6MTY1NzE4Mjk5M30.mhgASFgmbgPHS6Nml6E86JWQ98l6kDNmHwTIgan7jt4";
        mockMvc.perform(get("/api/v1/followers")
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void findFollowings() throws Exception {

        mockMvc.perform(get("/api/v1/followings")
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void deleteFollow() throws Exception {

                mockMvc.perform(delete("/api/v1/follow/" + "1")
                .header("X-AUTH-TOKEN", token))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
