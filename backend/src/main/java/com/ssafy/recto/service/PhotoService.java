package com.ssafy.recto.service;

import com.ssafy.recto.mapper.PhotoMapper;
import com.ssafy.recto.dto.Photo;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PhotoService {

	private final SqlSession sqlSession;

	@Autowired
	public PhotoService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public boolean insertPhoto(Photo photo, LocalDate date){
		//여기서 photo_id를 알려줄거임

		Date today = new Date();
		System.out.println(today);

		SimpleDateFormat date2 = new SimpleDateFormat("yyyyMMdd");

		String str = date2.format(today);
		System.out.println("Date: "+date2.format(today));

		str = str.substring(2,8);
		System.out.println("Date: "+str);
		int count = sqlSession.getMapper(PhotoMapper.class).allPhoto(str);

		if(count<10){
			photo.photo_id = str + "00"+Integer.toString(count+1);
		}else if(10<=count&& count<100){
			photo.photo_id = str + "0"+Integer.toString(count+1);
		}else if(100<=count&& count<1000){
			photo.photo_id = str +Integer.toString(count+1);
		}

		//만약 3자리면 넘어가고 ->
		//만약 2자리면

		return sqlSession.getMapper(PhotoMapper.class).insertPhoto(photo,date) == 1;
	}

	public Photo getPhoto(String photo_id) throws Exception{
		return sqlSession.getMapper(PhotoMapper.class).getPhoto(photo_id);
	}

	public List<Photo> getPhotoList(int user_seq) throws Exception{
		System.out.println("여기는 서비스야" + user_seq+"을 체크하려고해");

		return sqlSession.getMapper(PhotoMapper.class).getPhotoList(user_seq);
	}

//	public boolean modifyPhoto(Photo photo) throws Exception {
//		return sqlSession.getMapper(PhotoMapper.class).modifyPhoto(photo) == 1;
//	}

	public boolean deletePhoto(int photo_seq) throws Exception {
		return sqlSession.getMapper(PhotoMapper.class).deletePhoto(photo_seq) == 1 ;
	}

	public List<Photo> getSamplePhotoList() {
		return sqlSession.getMapper(PhotoMapper.class).getSamplePhotoList() ;
	}
}
