package com.ssafy.recto.mapper;

import org.apache.ibatis.annotations.*;
import com.ssafy.recto.dto.User;

@Mapper
public interface UserMapper {

//	@Select("select * from User where user_seq = #{user_seq}")
//	public User getUser(@Param("user_seq") int user_seq);
//
//	@Select("select * from User where user_id = #{user_id}")
//	public User userInfo(@Param("user_id") String user_id);
//
//	@Insert("insert INTO User ( user_seq, user_id, user_pwd, user_nickname, admin ) "
//			+ "VALUES ( #{user.user_seq}, #{user.user_id},#{user.user_pwd}, #{user.user_nickname}, #{user.admin})")
//	@Options(useGeneratedKeys = true)
//	public int signUp(@Param("user") User user);
//
//	@Select(" SELECT count(*) FROM User WHERE user_id = #{user.user_id} AND user_pwd = #{user.user_pwd} ")
//	public int login(@Param("user") User user);
}
