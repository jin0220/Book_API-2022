package com.example.book.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class Likes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ApiModelProperty(example = "책 제목")
    private String title;

    @ApiModelProperty(example = "책표지 이미지")
    private String image;

    @ApiModelProperty(example = "저자")
    private String author;

    @ApiModelProperty(example = "출판사")
    private String publisher;

    @ApiModelProperty(example = "책 분류")
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    @JsonBackReference // 자식클래스
    private User user;
}
