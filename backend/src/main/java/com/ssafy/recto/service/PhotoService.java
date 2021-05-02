package com.ssafy.recto.service;

import com.ssafy.recto.dao.PhotoDao;
import com.ssafy.recto.dto.Photo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
	private final SqlSession sqlSession;

	@Autowired
	public PhotoService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public int InsertPhoto(Photo photo){
		System.out.println("여기는 서비스야");
		PhotoDao.InsertPhoto(photo);
		System.out.println(photo.user_seq);
        return 0;
	}

	public Photo getPhoto(int photo_seq) throws Exception{
		return sqlSession.getMapper(PhotoDao.class).getPhoto(photo_seq);
	}
	
	public Photo getPhotoList(int user_seq) throws Exception{
		return sqlSession.getMapper(PhotoDao.class).getPhotoList(user_seq);
	}
	
	public boolean insertPhoto(Photo photo) throws Exception{
        return sqlSession.getMapper(PhotoDao.class).insertPhoto(photo) == 1;
    }

	public boolean modifyPhoto(Photo photo) throws Exception {
		return sqlSession.getMapper(PhotoDao.class).modifyPhoto(photo) == 1;
	}

	public boolean deletePhoto(int photo_seq) throws Exception {
		return sqlSession.getMapper(PhotoDao.class).deletePhoto(photo_seq) == 1 ;
	}
}
