package com.example.book.controller;

import com.example.book.entity.Likes;
import com.example.book.entity.response.Message;
import com.example.book.entity.response.StatusEnum;
import com.example.book.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LikesController {
    private final LikesService likesService;

    HttpHeaders responseHeaders;

    @PostConstruct  // WAS가 띄워질 때 실행
    public void init(){
        // 한글 깨짐 방지
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    /**
     * 사용자의 관심있는 책 전체 조회
     * */
    @GetMapping("/likes")
    public ResponseEntity<Message> findAll(HttpServletRequest request){
        String accessToken = request.getHeader("X-AUTH-TOKEN");

        List<Likes> likesList = likesService.findAll(accessToken);
        Message message = new Message();
        Map<String, List<Likes>> map = new HashMap<>();

        if(likesList != null){
            map.put("likeList", likesList);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("likeList", null);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 사용자가 관심있어하는 책 등록
     * */
    @PostMapping("/like")
    public ResponseEntity<Message> save(@RequestBody HashMap<String, Object> param, HttpServletRequest request) {
        Likes likes = new Likes();
        likes.setTitle(param.get("title").toString());
        likes.setAuthor(param.get("author").toString());
        likes.setImage(param.get("image").toString());
        likes.setPublisher(param.get("publisher").toString());
        likes.setCategory(param.get("category").toString());

        String accessToken = request.getHeader("X-AUTH-TOKEN");

        boolean check = likesService.save(accessToken, likes);
        Message message = new Message();
        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("result", true);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("result", false);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * 관심있어하는 책 취소
     * */
    @DeleteMapping("/like/{num}")
    public ResponseEntity<Message> deleteLike(@PathVariable Long num, HttpServletRequest request){
        String accessToken = request.getHeader("X-AUTH-TOKEN");

        boolean check = likesService.delete(accessToken, num);
        Message message = new Message();
        Map<String, Boolean> map = new HashMap<>();

        if(check){
            map.put("result", true);

            message.setMessage("Success");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        else {
            map.put("result", false);

            message.setMessage("Fail");
            message.setStatus(StatusEnum.OK);
            message.setData(map);
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
