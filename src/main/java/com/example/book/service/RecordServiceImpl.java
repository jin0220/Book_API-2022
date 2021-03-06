package com.example.book.service;

import com.example.book.entity.Record;
import com.example.book.entity.User;
import com.example.book.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService{

    private final RecordRepository recordRepository;
    private final AuthService authService;

    @Override
    public boolean save(Record record, String token) {
        User user = authService.getUserId(token);

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
        User user = authService.getUserId(token);
        if(user != null)
            return recordRepository.findByDateContaining(date);
        else
            return null;
    }

    @Override
    public Optional<Record> findOne(Long num, String token) {
        User user = authService.getUserId(token);
        if(user != null)
            return recordRepository.findById(num);
        else
            return null;
    }

    @Override
    public boolean update(Long num, Record record, String token) {
        User user = authService.getUserId(token);

        if(user != null){
            Optional<Record> record1 = recordRepository.findById(num);
            if(record1 != null) {
                record.setId(num);
                recordRepository.save(record);
                return true;
            }
            else return false;
        }
        return false;
    }

    @Override
    public boolean delete(Long num, String token) {
        User user = authService.getUserId(token);
        Record record = new Record();
        record.setId(num);
        if(user != null){
            recordRepository.delete(record);
            return true;
        }

        return false;
    }


}
