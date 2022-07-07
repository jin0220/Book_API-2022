package com.example.book.service;

import com.example.book.configuration.JwtTokenProvider;
import com.example.book.entity.User;
import com.example.book.entity.response.Message;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    /**
     * 토큰을 재발급하는 과정
     * */
    public Map<String, String> issueAccessToken(HttpServletRequest request){
//        String accessToken = jwtTokenProvider.resolveToken(request);
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String userId = null;
        String accessToken = null;
        //accessToken이 만료됐고 refreshToken이 맞으면 accessToken을 새로 발급
//        if(!jwtTokenProvider.validationToken(accessToken)){  //클라이언트에서 토큰 재발급 api로의 요청을 확정해주면 이 조건문은 필요 없을 것 같다.
//            System.out.println("Access 토큰 만료됨");
            if(jwtTokenProvider.validationToken(refreshToken)){     //들어온 Refresh 토큰이 자체적으로 유효한지
                System.out.println("Refresh 토큰은 유효함");
                userId  = jwtTokenProvider.getUserPk(refreshToken);

                User user = userRepository.findUserById(userId);
                String tokenFromDB = user.getRefreshToken();
                if(refreshToken.equals(tokenFromDB)) {   //DB의 원본 refresh토큰과 지금들어온 토큰이 같은지 확인
                    System.out.println("Access 토큰 재발급 완료");
                    accessToken = jwtTokenProvider.createToken(userId);
                }
                else{
                    //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                    //예외발생
                    System.out.println("Refresh Token Tampered");
                }
            }
            else{
                //입력으로 들어온 Refresh 토큰이 유효하지 않음
                return null;
            }
//        }


        Map<String, String> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        map.put("userId", userId);

        return map; // 발급할때 리프레시 토큰도 다시 재발급할 수 있도록 추후 수정
    }

    /**
     * 토큰으로부터 사용자 정보 가져오기
     * */
    public User getUserId(String token) {
        // 토큰에서 회원 id 가져오기
        Authentication authentication = jwtTokenProvider.getAuthentication(token);

        // 가져온 회원 아이디 기반으로 회원 데이터 조회
        User user = userRepository.findUserById(authentication.getName());

        return user;
    }

    /**
     * 로그인하면 DB에 refresh token 저장
     * @param refreshToken 로그인 시 발급받은 refresh token
     * @param userId 사용자의 id
     * @return DB 저장 여부
     * */
    public boolean updateRefreshToken(String refreshToken, String userId){
        int check = userRepository.updateRefreshToken(refreshToken, userId);
        if(check == 1) return true;
        else return false;
    }
}
