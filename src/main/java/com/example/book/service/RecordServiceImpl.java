package com.example.book.service;

import com.example.book.entity.Record;
import com.example.book.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;

    @Override
    public boolean save(Record record) {
        Record check = recordRepository.save(record);
        if(check != null) // 저장되었을 경우
            return true;
        else
            return false;
    }

    @Override
    public List<Record> findList(String date) {
        return recordRepository.findByDateContaining(date);
    }

    @Override
    public Optional<Record> findOne(Long num) {
        return recordRepository.findById(num);
    }
}
