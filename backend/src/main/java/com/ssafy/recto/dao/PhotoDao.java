package com.ssafy.recto.dao;

import com.ssafy.recto.dto.Photo;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Mapper
public interface PhotoDao {

    @Insert("Insert INTO photo ( photo_seq, user_seq, photo_id, photo_date, video_url, pharse, photo_pwd, design, publication) \n" +
            "VALUES ( #{photo.photo_seq},#{photo.user_seq},#{photo.photo_id},#{photo.photo_date},#{photo.video_url},#{photo.pharse},#{photo.photo_pwd},#{photo.design},#{photo.publication})")
    @Options(useGeneratedKeys = true)
    int insertPhoto(@Param("photo") Photo photo) ;


    @Select("SELECT * FROM photo WHERE photo_seq = #{photo_seq}")
    Photo getPhoto(@Param("photo_seq")int photo_seq);

    @Select("SELECT * FROM photo WHERE user_seq = #{user_seq}")
    public List<Photo> getPhotoList(@Param("user_seq")int user_seq);

    @Update("update photo set " +
            "photo_id = #{photo.photo_id}," +
            "photo_date = #{photo.photo_date}, " +
            "user_seq=#{photo.user_seq}, " +
            "video_url = #{photo.video_url}, " +
            "pharse = #{photo.pharse}, " +
            "photo_pwd = #{photo.photo_pwd}, " +
            "design =  #{photo.design}, " +
            "publication = #{photo.publication}" +
            " where photo_seq = #{photo.photo_seq}")
    public int modifyPhoto(@Param("photo") Photo photo);

    @Delete("delete from photo where photo_seq = #{photo_seq}")
    public int deletePhoto(@Param("photo_seq")int photo_seq);
}
