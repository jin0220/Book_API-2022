package com.example.book.configuration;

import lombok.Builder;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /*
    * 자원에 대한 접근 풀기
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/script/**", "image/**", "/fonts/**", "lib/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // 403에러로 추가
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN") // /admin 경로의 모든 경로는 ADMIN권한 사용자만 접근
                .antMatchers("/**").permitAll(); // 모든 경로에 권한없이 접근 가능
    }
}
