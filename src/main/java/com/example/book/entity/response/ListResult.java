package com.example.book.entity.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
* 다중 응답 모델
*/

@Setter @Getter
public class ListResult<T> extends CommonResult {
    private List<T> data;
}
