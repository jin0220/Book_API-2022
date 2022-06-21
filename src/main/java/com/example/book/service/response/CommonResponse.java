package com.example.book.service.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResponse {
    SUCCESS(200, "SUCCESS"),
    FAIL(-1, "FAIL");

    private int code;
    private String msg;
}
