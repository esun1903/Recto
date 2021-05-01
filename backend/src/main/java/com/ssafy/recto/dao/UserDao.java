package com.ssafy.recto.dao;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.recto.dto.User;

@Mapper
public interface UserDao {
	public User getUser(int user_seq) throws SQLException;
	public User userInfo(String user_id);
	public int signUp(User user) throws SQLException;
    public int login(User user) throws SQLException;
}
