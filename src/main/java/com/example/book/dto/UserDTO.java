package com.example.book.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private String id;
    private String accessToken;
    private String refreshToken;

    public UserDTO(String id, String token, String refreshToken) {
        this.id = id;
        this.accessToken = token;
        this.refreshToken = refreshToken;
    }
}
