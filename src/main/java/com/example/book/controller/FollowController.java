package com.example.book.controller;

import com.example.book.entity.Follow;
import com.example.book.entity.Likes;
import com.example.book.entity.User;
import com.example.book.entity.response.Message;
import com.example.book.entity.response.StatusEnum;
import com.example.book.service.FollowService;
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
public class FollowController {

    private final FollowService followService;

    HttpHeaders responseHeaders;

    @PostConstruct  // WAS가 띄워질 때 실행
    public void init(){
        // 한글 깨짐 방지
        responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json; charset=UTF-8");
    }

    /**
     * 사용자의 팔로워 리스트 조회 (나를 친구로 추가한 사람)
     * */
    @GetMapping("/followers")
    public ResponseEntity<Message> findFollowers(HttpServletRequest request){
        String accessToken = request.getHeader("X-AUTH-TOKEN");

        List<User> follower = followService.findFollower(accessToken);
        Message message = new Message();
        Map<String, List<User>> map = new HashMap<>();

        map.put("likeList", follower);

        message.setMessage("Success");
        message.setStatus(StatusEnum.OK);
        message.setData(map);

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 사용자의 팔로잉 리스트 조회 (내가 친구로 추가한 사람)
     **/
    @GetMapping("/followings")
    public ResponseEntity<Message> findFollowings(HttpServletRequest request){
        String accessToken = request.getHeader("X-AUTH-TOKEN");

        List<User> follower = followService.findFollowing(accessToken);
        Message message = new Message();
        Map<String, List<User>> map = new HashMap<>();

        map.put("likeList", follower);

        message.setMessage("Success");
        message.setStatus(StatusEnum.OK);
        message.setData(map);

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 팔로우 하기
     * @param param 사용자가 팔로우하는 사용자 계정
     * */
    @PostMapping("/follow")
    public ResponseEntity<Message> save(HttpServletRequest request, @RequestBody HashMap<String, Object> param){
        String accessToken = request.getHeader("X-AUTH-TOKEN");
        String toUser = param.get("id").toString();

        boolean check = followService.save(accessToken, toUser);
        Message message = new Message();
        Map<String, Boolean> map = new HashMap<>();

        if(check) {
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

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }

    /**
     * 팔로우 취소하기
     * */
    @DeleteMapping("/follow/{num}")
    public ResponseEntity<Message> deleteFollow(HttpServletRequest request, @PathVariable Long num){
        String accessToken = request.getHeader("X-AUTH-TOKEN");

        boolean check = followService.delete(accessToken, num);
        Message message = new Message();
        Map<String, Boolean> map = new HashMap<>();

        if(check) {
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

        return new ResponseEntity<>(message, responseHeaders, HttpStatus.OK);
    }
}
