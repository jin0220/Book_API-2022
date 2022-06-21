package com.example.book.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECORD_ID")
    private long id;

    @ApiModelProperty(example = "책 제목")
    private String title;

    @ApiModelProperty(example = "책표지 이미지")
    private String image;

    @ApiModelProperty(example = "저자")
    private String author;

    @ApiModelProperty(example = "출판사")
    private String publisher;

    @ApiModelProperty(example = "사용자 평가 등급")
    private double rating;

    @ApiModelProperty(example = "사용자의 기록 내용")
    private String memo;

    @ApiModelProperty(example = "글 작성 날짜")
    private String date;

    @ApiModelProperty(example = "책 분류")
    private String category;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "USER_ID")
//    private User user;
}
