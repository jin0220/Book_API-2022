package com.example.book.repository;

import com.example.book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);
    User findUserById(String id);
}
