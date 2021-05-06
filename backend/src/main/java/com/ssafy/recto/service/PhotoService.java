package com.ssafy.recto.service;

import com.ssafy.recto.mapper.PhotoMapper;
import com.ssafy.recto.dto.Photo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

	private final SqlSession sqlSession;
	
	@Autowired
	public PhotoService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public boolean insertPhoto(Photo photo){
		return sqlSession.getMapper(PhotoMapper.class).insertPhoto(photo) == 1;
	}

	public Photo getPhoto(String photo_id) throws Exception{
		return sqlSession.getMapper(PhotoMapper.class).getPhoto(photo_id);
	}
	
	public List<Photo> getPhotoList(int user_seq) throws Exception{
		System.out.println("여기는 서비스야" + user_seq+"을 체크하려고해");

		return sqlSession.getMapper(PhotoMapper.class).getPhotoList(user_seq);
	}

	public boolean modifyPhoto(Photo photo) throws Exception {
		return sqlSession.getMapper(PhotoMapper.class).modifyPhoto(photo) == 1;
	}

	public boolean deletePhoto(int photo_seq) throws Exception {
		return sqlSession.getMapper(PhotoMapper.class).deletePhoto(photo_seq) == 1 ;
	}
}
