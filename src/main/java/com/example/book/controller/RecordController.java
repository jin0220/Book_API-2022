package com.example.book.controller;

import com.example.book.entity.Record;
import com.example.book.entity.response.Message;
import com.example.book.entity.response.StatusEnum;
import com.example.book.service.RecordService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record")
public class RecordController {
    private final RecordService recordService;

    @ResponseBody
    @PostMapping("")
    @ApiOperation(value = "책에 대한 기록 저장")
    public ResponseEntity<Message> save(@RequestBody Record record){
        boolean check = recordService.save(record);

        Map<String, Boolean> map = new HashMap<>();

        Message message = new Message();

        if(check){
            map.put("result", true);

            message.setMessage("Record Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else{
            map.put("result", false);

            message.setMessage("Record Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/record-list")
    @ApiOperation(value = "달력을 클릭했을 때 해당 날짜에 대한 기록 조회")
    public ResponseEntity<Message> findList(@PathVariable String date){
        recordService.findList(date);

        Message message = new Message();

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/detail/{num}")
    @ApiOperation(value = "기록의 상세 페이지")
    public Optional<Record> findOne(@PathVariable Long num){
        return recordService.findOne(num);
    }


}
