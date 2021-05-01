package com.ssafy.recto.dao;

import com.ssafy.recto.dto.Photo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoDao {

    @Insert("Insert INTO Photo ( photo_seq, user_seq, photo_id, photo_date, video_url, pharse, photo_pwd, design, publication) \n" +
            "VALUES ( #{photo.photo_seq},#{photo.user_seq},#{photo.photo_id},#{photo.photo_date},#{photo.video_url},#{photo.pharse},#{photo.photo_pwd},#{photo.design},#{photo.publication})")
    @Options(useGeneratedKeys = true)
    public int InsertPhoto(@Param("photo") Photo photo);
}
