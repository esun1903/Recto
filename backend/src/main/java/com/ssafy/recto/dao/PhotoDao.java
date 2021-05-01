package com.ssafy.recto.dao;

import com.ssafy.recto.dto.Photo;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoDao {

    @Insert("Insert INTO Photo ( photo_seq, user_seq, photo_id, photo_date, video_url, pharse, photo_pwd, design, publication) \n" +
            "VALUES ( #{photo.photo_seq},#{photo.user_seq},#{photo.photo_id},#{photo.photo_date},#{photo.video_url},#{photo.pharse},#{photo.photo_pwd},#{photo.design},#{photo.publication})")
    @Options(useGeneratedKeys = true)
    public static int InsertPhoto(@Param("photo") Photo photo) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Photo getPhoto(int photo_seq) throws SQLException;
	public Photo getPhotoList(int user_seq) throws SQLException;
    public int insertPhoto(Photo photo) throws SQLException;
    public int modifyPhoto(Photo photo) throws SQLException;
    public int deletePhoto(int photo_seq) throws SQLException;
}
