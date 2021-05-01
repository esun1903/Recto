package com.ssafy.recto.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PresentService {
	private final SqlSession sqlSession;

	@Autowired
	public PresentService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

}
