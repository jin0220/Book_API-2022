package com.example.book.repository;

import com.example.book.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);
    User findUserById(String id);
    User findUserByRefreshToken(String token);

    @Modifying // jpa에서 @Query를 사용하여 update 시 필요한 어노테이션
    @Transactional // 이 어노테이션도 필요함.
    @Query(value = "update User set refreshToken=:refreshToken where id=:userId") // jpql로 작성시 테이블명과 필드명은 엔티티명과 속성명으로 작성해야함.
    int updateRefreshToken(@Param("refreshToken") String refreshToken, @Param("userId") String userId);
}
