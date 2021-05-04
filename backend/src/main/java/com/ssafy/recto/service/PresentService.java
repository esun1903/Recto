package com.ssafy.recto.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recto.dto.Present;
import com.ssafy.recto.mapper.PresentMapper;

@Service
public class PresentService {
	private final SqlSession sqlSession;

	@Autowired
	public PresentService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public boolean sendPresent(Present present){
		return sqlSession.getMapper(PresentMapper.class).sendPresent(present) == 1;
	}

	public Present getPresent(int photo_seq) throws Exception{
		return sqlSession.getMapper(PresentMapper.class).getPresent(photo_seq);
	}
	
	public List<Present> getPresentList(int user_seq) throws Exception{
		return sqlSession.getMapper(PresentMapper.class).getPresentList(user_seq);
	}

}
