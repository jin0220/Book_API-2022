package com.example.book.service;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.Record;
import com.example.book.entity.User;
import com.example.book.repository.RecordRepository;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 임시 메소드
    public User getUserId(String token) {
        // 토큰에서 회원 id 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        // 가져온 회원 아이디 기반으로 회원 데이터 조회
        User user = userRepository.findUserById(authentication.getName());

        return user;
    }

    @Override
    public boolean save(Record record, String token) {
        User user = getUserId(token);

        if(user != null) { // 회원 조회가 되었을 경우, 기록을 저장
            record.setUser(user);
            Record check = recordRepository.save(record);

            if (check != null) // 저장되었을 경우
                return true;
            else
                return false;
        }
        else {
            return false;
        }
    }

    @Override
    public List<Record> findList(String date, String token) {
        User user = getUserId(token);
        if(user != null)
            return recordRepository.findByDateContaining(date);
        else
            return null;
    }

    @Override
    public Optional<Record> findOne(Long num, String token) {
        User user = getUserId(token);
        if(user != null)
            return recordRepository.findById(num);
        else
            return null;
    }
}
