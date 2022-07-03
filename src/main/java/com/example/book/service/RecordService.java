package com.example.book.service;

import com.example.book.entity.Record;
import org.springframework.data.repository.query.Param;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface RecordService {
    // 기록 저장
    public boolean save(Record record, String request);

    // 달력을 클릭했을 때 해당 날짜에 대한 기록 조회
    public List<Record> findList(String date, String token);

    // 기록의 상세 페이지
    public Optional<Record> findOne(Long num, String token);

    // 기록 수정
    public boolean update(Long num, Record record, String token);

    // 기록 삭제
    public boolean delete(Long num, String token);
}
