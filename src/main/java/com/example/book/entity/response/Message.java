package com.example.book.entity.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Message {

    private StatusEnum status;
    private String message;
    private Object data;

    public Message() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.message = null;
    }
}
