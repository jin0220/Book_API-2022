package com.example.book.service;

import com.example.book.entity.User;
import com.example.book.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor // 의존성 주입
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
    * 회원정보 추가(회원가입)
    * @param user 회원 가입 폼으로부터 전달받은 정보
    * @return 이미 저장된 email 여부에 따라 사용자 추가가 되었는지에 대한 boolean 값.
    */
    public boolean save(User user){
        // 이미 저장된 email이 있을 경우
        if(userRepository.findUserByEmail(user.getEmail()) != null)
            return false;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // 회원 비밀번호 암호화
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    /**
    * 로그인
    * @param userInfo 사용자의 입력 정보
    * @return 회원정보 존재 여부 및 비밀번호 일치 여부
    */
    public boolean login(User userInfo){
        // 회원 정보가 존재하는지 확인
        User user = userRepository.findUserById(userInfo.getId());
        if(user == null) return false;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 회원 비밀번호 일치 여부 확인
        if(!encoder.matches(userInfo.getPassword(), user.getPassword())) {
            return false;
        }

        return true;
    }

    /**
     * Spring Security 필수 메소드
     * @param id 입력받은 사용자 아이디
     * @return id로 찾은 사용자 정보
     * @throws UsernameNotFoundException
     *
     * SpringSecurity의 속성으로 지정한 loginProcessingUrl("URL")에 해당하는 URL이 호출될 때 수행되어진다.
     * password 값이 일치하는지 확인하는 코드는 없지만, 시큐리티가 알아서 내부적으로 확인해줌.
     */
    @Override
    public User loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findUserById(id);

        if(user == null)  return null;

        return user;
    }
}
