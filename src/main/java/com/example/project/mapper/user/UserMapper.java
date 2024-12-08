package com.example.project.mapper.user;

import com.example.project.dto.user.UserDTO;
import com.example.project.dto.user.UserSessionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void insertUser(UserDTO userDTO);

    //@Param : MyBatis에서 SQL 쿼리로 전달되는 파라미터에 이름을 지정하기 위해 사용한다
    //여러개의 파라미터가 있을 때 가독성을 높이고 sql 쿼리 내에서 매개변수의 이름을 명확하게 사용할 수 있다
    Optional<Long> selectId(@Param("loginId")String loginId, @Param("password") String password);

    Optional<UserSessionDTO> selectLoginInfo(@Param("loginId") String loginId, @Param("password") String password);

}
