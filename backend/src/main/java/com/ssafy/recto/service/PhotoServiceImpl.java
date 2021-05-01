package com.ssafy.recto.service;

import com.ssafy.recto.dao.PhotoDao;
import com.ssafy.recto.dto.Photo;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

	PhotoDao photoDao;
	@Override
	public int InsertPhoto(Photo photo){
		System.out.println("여기는 서비스야");
		photoDao.InsertPhoto(photo);
		System.out.println(photo.user_seq);
        return 0;
	};

}
