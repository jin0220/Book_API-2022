package com.example.book.controller;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.Record;
import com.example.book.entity.response.Message;
import com.example.book.entity.response.StatusEnum;
import com.example.book.service.AuthService;
import com.example.book.service.RecordService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RecordController {
    private final RecordService recordService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 헤더에 포함된 access token 정보와 사용자 입력 정보를 받는다.
     * @param record 사용자가 입력한 기록 정보
     * @param request 헤더에 포함된 access token 정보를 가져오기 위해 사용
     * @return 저장 성공 여부
     * {
         "status":"OK",
         "message":"Record Success" / "Record Fail" ,
         "data":{
             "result":true / false
            }
        }
     */
    @PostMapping("/record")
    @ApiOperation(value = "책에 대한 기록 저장")
    public ResponseEntity<Message> save(@RequestBody HashMap<String, Object> param, HttpServletRequest request){
        Record record = new Record();
        record.setTitle(param.get("title").toString());
        record.setAuthor(param.get("author").toString());
        record.setImage(param.get("image").toString());
        record.setPublisher(param.get("publisher").toString());
        record.setMemo(param.get("memo").toString());
        record.setDate(param.get("date").toString());
        record.setRating((double) param.get("rating"));
        record.setCategory(param.get("category").toString());

        String accessToken = jwtTokenProvider.resolveToken(request);

        boolean check = recordService.save(record, accessToken);

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

    /**
     * 사용자의 입력에 따른 기록 조회
     * @param date 사용자가 입력한 날짜 정보
     * @param request 헤더에 포함된 access token 정보를 가져오기 위해 사용
     * @return 조회 결과 반환
     * {
        "status":"OK",
        "message":"Success" / "Fail" ,
        "data":{
            "list":[{},{},...]
        }
        }
     */
    @GetMapping("/record-list/{date}")
    @ApiOperation(value = "달력을 클릭했을 때 해당 날짜에 대한 기록 조회")
    public ResponseEntity<Message> findList(@PathVariable String date, HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveToken(request);

        List<Record> recordList = recordService.findList(date, accessToken);

        // 한글 깨짐 방지
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

        Message message = new Message();

        Map<String, List<Record>> map = new HashMap<>();

        if(recordList != null){
            map.put("list", recordList);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else{
            map.put("list", null);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 기록의 상세 페이지
     * @param num 사용자가 보고 싶은 기록 정보의 id
     * @param request 헤더에 포함된 access token 정보를 가져오기 위해 사용
     * @return 조회 결과 반환
     * {
        "status":"OK",
        "message":"Success" / "Fail" ,
        "data":{
            "data":{}
            }
        }
     */
    @GetMapping("/record-detail/{num}")
    @ApiOperation(value = "기록의 상세 페이지")
    public ResponseEntity<Message> findOne(@PathVariable Long num, HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveToken(request);

        Optional<Record> record = recordService.findOne(num, accessToken);

        // 한글 깨짐 방지
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");

        Message message = new Message();

        Map<String, Optional<Record>> map = new HashMap<>();

        if(record != null){
            map.put("data", record);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else{
            map.put("data", null);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }


        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 기록 수정
     * @param num 수정하고자 하는 레코드 id 값
     * @param record 사용자 입력
     * @return
      {"status":"OK","message":"Success","data":{"result":true}}
     *  */
    @PutMapping("/record/{num}")
    @ApiOperation(value = "기록 수정")
    public ResponseEntity<Message> update(@PathVariable Long num, @RequestBody HashMap<String, Object> param, HttpServletRequest request){
        Record record = new Record();
        record.setTitle(param.get("title").toString());
        record.setAuthor(param.get("author").toString());
        record.setImage(param.get("image").toString());
        record.setPublisher(param.get("publisher").toString());
        record.setMemo(param.get("memo").toString());
        record.setDate(param.get("date").toString());
        record.setRating((double)param.get("rating"));
        record.setCategory(param.get("category").toString());

        String accessToken = jwtTokenProvider.resolveToken(request);
        boolean check = recordService.update(num, record, accessToken);

        Message message = new Message();

        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("result", true);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else{
            map.put("result", false);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/record/{num}")
    @ApiOperation(value = "기록 삭제")
    public ResponseEntity<Message> deleteRecord(@PathVariable Long num, HttpServletRequest request){
        String accessToken = jwtTokenProvider.resolveToken(request);
        boolean check = recordService.delete(num, accessToken);

        Message message = new Message();

        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("result", true);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else{
            map.put("result", false);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
