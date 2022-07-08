package com.example.book.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Setter @Getter
public class User implements UserDetails {
    @Id
    @Column(name = "USER_ID")
    @ApiModelProperty(example = "회원 아이디")
    private String id;

    @ApiModelProperty(example = "회원 비밀번호")
    private String password;

    @ApiModelProperty(example = "회원 이메일")
    private String email;

    @ApiModelProperty(example = "회원 프로필 사진")
    private String profileImage;

    @ApiModelProperty(example = "회원의 리프레시 토큰")
    private String refreshToken;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Record> recordList = new ArrayList<>();

    @OneToMany(mappedBy = "fromUser")
    @JsonManagedReference
    private List<Follow> fromUserList = new ArrayList<>();

    @OneToMany(mappedBy = "toUser")
    @JsonManagedReference
    private List<Follow> toUserList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Likes> likesList = new ArrayList<>();



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void changeRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
