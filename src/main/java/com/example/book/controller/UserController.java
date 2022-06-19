package com.example.book.controller;

import com.example.book.entity.User;
import com.example.book.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/v1/user")
    @ApiOperation(value = "신규 회원 정보 입력")
    public boolean singUp(@RequestBody User user){
        return userService.save(user);
    }
}
