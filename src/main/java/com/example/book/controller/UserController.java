package com.example.book.controller;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.User;
import com.example.book.entity.response.Message;
import com.example.book.entity.response.StatusEnum;
import com.example.book.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;


    /**
     * 회원 가입
     * @param user 사용자가 입력한 회원 정보
     * @return 회원 가입의 성공 여부
        {
          "status": "OK",
          "message": "Sign Up Success" / "Sign Up Fail",
          "data": {
                "result": true / false
          }
        }
     */
    @ResponseBody
    @PostMapping("/signup")
    @ApiOperation(value = "신규 회원 정보 입력")
    public ResponseEntity<Message> singUp(@RequestBody User user){
        boolean check = userService.save(user);

        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("result", true);

            Message message = new Message();
            message.setMessage("Sign Up Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            map.put("result", false);

            Message message = new Message();
            message.setMessage("Sign Up Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
    }

    /**
     * 로그인
     * @param user 사용자가 입력한 아이디, 비밀번호
     * @return 로그인이 성공했을 경우 토큰 전달
        {
            "status": "OK",
            "message": "Sign Up Success" / "Sign Up Fail",
            "data": {
                "token": ""
            }
        }
     */
    @ResponseBody
    @PostMapping(value = "/signin",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Message> login(@RequestBody User user) {
        boolean check = userService.login(user);
        // 회원 정보가 일치하면 토큰 생성
        if(check) {
            String token = jwtTokenProvider.createToken(user.getId());

            Map<String, String> map = new HashMap<>();
            map.put("token", token);

            Message message = new Message();
            message.setMessage("Login Success");
            message.setData(map);
            message.setStatus(StatusEnum.OK);

            return new ResponseEntity<>(message, HttpStatus.OK);
        }
        else {
            Message message = new Message();
            message.setMessage("Login Fail");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }
//        return false;
    }
}
