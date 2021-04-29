package com.ssafy.recto.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	private final SqlSession sqlSession;

	@Autowired
	public UserService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
