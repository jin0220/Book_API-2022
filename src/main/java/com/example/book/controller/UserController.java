package com.example.book.controller;

import com.example.book.entity.User;
import com.example.book.entity.response.Message;
import com.example.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;


    @PostMapping("/user")
    @ApiOperation(value = "신규 회원 정보 입력")
    public boolean singUp(@RequestBody User user){
        return userService.save(user);
    }

    @PostMapping("/signin")
    public boolean login(User user) {
        return userService.login(user);
//        return false;
    }
}
