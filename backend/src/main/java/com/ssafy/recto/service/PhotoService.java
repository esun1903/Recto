package com.ssafy.recto.service;


import com.ssafy.recto.dto.Photo;

import org.springframework.stereotype.Service;

@Service
public interface PhotoService {

	public int InsertPhoto(Photo photo);

}
