package com.example.book.service;

import com.example.book.entity.Record;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RecordService {
    // 기록 저장
    public boolean save(Record record);

    // 달력을 클릭했을 때 해당 날짜에 대한 기록 조회
    public List<Record> findList(String date);

    // 기록의 상세 페이지
    public Optional<Record> findOne(Long num);
}
