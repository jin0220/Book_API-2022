package com.example.book.entity.response;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

/**
* 공통 응답 모델
*/

@Setter @Getter
public class CommonResult {
    @ApiModelProperty(value = "응답 성공 여부: T/F")
    private boolean success;

    @ApiModelProperty(value = "응답 코드: >= 0 정상, < 0 비정상")
    private int code;

    @ApiModelProperty(value = "응답 메시지")
    private String msg;
}
