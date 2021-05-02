package com.ssafy.recto.service;

import com.ssafy.recto.dao.PhotoDao;
import com.ssafy.recto.dto.Photo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {

	private final SqlSession sqlSession;

	public final PhotoDao photoDao;

	public PhotoService(SqlSession sqlSession, PhotoDao photoDao) {
		this.sqlSession = sqlSession;
		this.photoDao = photoDao;
	}
	
	public boolean InsertPhoto(Photo photo){
		return sqlSession.getMapper(PhotoDao.class).insertPhoto(photo) == 1;
	}

	public Photo getPhoto(int photo_seq) throws Exception{
		Photo photo = photoDao.getPhoto(photo_seq);
		return photo;
	}
	
	public List<Photo> getPhotoList(int user_seq) throws Exception{
		System.out.println("여기는 서비스야" + user_seq+"을 체크하려고해");

		List<Photo> photo = photoDao.getPhotoList(user_seq);
		System.out.println(photo.toString());
		return photo;
	}


	public boolean modifyPhoto(Photo photo) throws Exception {
		return photoDao.modifyPhoto(photo) == 1;
	}

	public boolean deletePhoto(int photo_seq) throws Exception {
		return photoDao.deletePhoto(photo_seq) == 1 ;
	}
}
