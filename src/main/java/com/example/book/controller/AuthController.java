package com.example.book.controller;

import com.example.book.dto.UserDTO;
import com.example.book.entity.response.Message;
import com.example.book.service.AuthService;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    @ApiOperation(value = "토큰 재발급", notes = "토큰을 재발급힌다.")
    @PostMapping(value = "/refresh")
    public ResponseEntity<Message> refreshToken(HttpServletRequest request) {
        Map<String, String> map = authService.issueAccessToken(request);

        // 사용자의 refresh token 업데이트
        authService.updateRefreshToken(map.get("refreshToken"), map.get("userId"));

        Message message = new Message();
        message.setMessage("Token 발급");
        message.setData(map);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
