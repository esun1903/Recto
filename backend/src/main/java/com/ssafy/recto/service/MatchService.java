package com.ssafy.recto.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
	private final SqlSession sqlSession;

	@Autowired
	public MatchService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
