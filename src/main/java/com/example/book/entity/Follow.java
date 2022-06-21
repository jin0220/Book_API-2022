package com.example.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter @Getter
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "from_user_id")
//    private User fromUser;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "to_user_id")
//    private User toUser;
}
