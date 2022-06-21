package com.example.book.controller;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.User;
import com.example.book.entity.response.SingleResult;
import com.example.book.repository.UserRepository;
import com.example.book.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SignController {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder; // 비밀번호 암호화

/*    @PostMapping("/signin")
    public User login(@RequestBody User userInfo){
        userRepository.findUserById()
    }*/
}
