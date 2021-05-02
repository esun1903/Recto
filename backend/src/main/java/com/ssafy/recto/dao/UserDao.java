package com.ssafy.recto.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.*;
import com.ssafy.recto.dto.User;

@Mapper
public interface UserDao {

    @Select("select * from user where user_seq = #{user_seq}")
    User getUser(@Param("user_seq") int user_seq);

    @Select("select * from user where user_id = #{user_id}")
    User userInfo(@Param("user_id") String user_id);

    @Insert("Insert INTO user ( user_seq, user_id, user_pwd, user_nickname, manager, user_image ) " +
            "VALUES ( #{user.user_seq}, #{user.user_id},#{user.user_pwd}, #{user.user_nickname}, #{user.manager}, #{user.user_image})")
    @Options(useGeneratedKeys = true)
    int signUp(@Param("user") User user);


    @Select(" SELECT * FROM user WHERE user_id = #{user.user_id} AND user_pwd = #{user.user_pwd} ")
    User login(@Param("user") User user);
}
