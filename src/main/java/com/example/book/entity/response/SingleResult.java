package com.example.book.entity.response;

import lombok.Getter;
import lombok.Setter;

/**
* 단일 응답 모델
*/

@Setter @Getter
public class SingleResult<T> extends CommonResult {
    private T data;
}
