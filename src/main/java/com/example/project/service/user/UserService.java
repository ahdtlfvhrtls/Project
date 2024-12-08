package com.example.project.service.user;

import com.example.project.dto.user.UserDTO;
import com.example.project.dto.user.UserSessionDTO;
import com.example.project.mapper.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;

    //유저 추가 메소드
    public void registerUser(UserDTO userDTO){
        userMapper.insertUser(userDTO);
    }

    //유저 번호 찾기 메소드
    public Long findId(String loginId, String password){
        log.info(loginId, password);
        return userMapper.selectId(loginId, password)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 정보"));
    }

    public UserSessionDTO findLoginInfo(String loginId, String password){
        return userMapper.selectLoginInfo(loginId, password)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원 정보"));
    }

}
