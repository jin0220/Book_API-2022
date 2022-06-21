package com.example.book.controller;

import com.example.book.entity.User;
import com.example.book.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;

    @Test
    @Transactional // 트랜잭션을 자동으로 수행, 예외나 에러가 발생하면 test 중 db에 입력된 값들을 rollback 해준다.
    @Rollback(value = false) // 테스트 중 db에 입력된 값을 테스트 전 원래상태로 rollback 해준다. default는 true
    public void signUp(){
        User user = new User();
        user.setId("test1");
        user.setPassword("1234");
        user.setEmail("test1@email.com");
        user.setProfileImage(null);

        boolean check = userService.save(user);

        if(check){
            System.out.println("회원가입을 축하합니다.");
        }
        else{
            System.out.println("이미 회원가입 되어있는 이메일입니다.");
        }
    }

    @Test
    public void login(){
        User user = new User();
        user.setId("test1");
        user.setPassword("1234");

        boolean check = userService.login(user);

        if(check){
            System.out.println("로그인 성공!");
        }
        else {
            System.out.println("아이디 또는 비밀번호가 틀렸습니다.");
        }
    }
}