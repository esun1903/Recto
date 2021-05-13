package com.ssafy.recto.mapper;

import com.ssafy.recto.dto.Photo;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PhotoMapper {

    @Insert("Insert INTO photo (  user_uid, photo_id, photo_date, photo_url, video_url, phrase, photo_pwd, design, publication) \n"
            + "VALUES ( #{photo.user_uid},#{photo.photo_id},#{date},#{photo.photo_url},#{photo.video_url},#{photo.phrase}," +
            " #{photo.photo_pwd},#{photo.design},#{photo.publication})")
    @Options(useGeneratedKeys = true)
    public int insertPhoto(@Param("photo") Photo photo, @Param("date") LocalDate date);

    @Select("SELECT * FROM photo WHERE photo_id like #{photo_id}")
    public Photo getPhoto(@Param("photo_id") String photo_id);

    @Select("SELECT * FROM photo WHERE photo_seq = #{photo_seq}")
    public Photo getPhotoDetail(@Param("photo_seq") int photo_seq);

    @Select("SELECT * FROM photo WHERE user_uid like #{user_uid}")
    public List<Photo> getPhotoList(@Param("user_uid") String user_uid);

    @Select("SELECT * FROM photo WHERE user_uid like #{user_uid} order by photo_seq desc limit 5")
    public List<Photo> getPhotoMain(@Param("user_uid") String user_uid);

    @Select("SELECT COUNT(*) FROM photo WHERE photo_id LIKE CONCAT(#{nowDay}, '%')")
    public int allPhoto(@Param("nowDay") String nowDay);




//	CONCAT(‘%’, #{searchKeyword},
//	@Update("update photo set " + "photo_date = #{photo.photo_date}, " + "photo_url = #{photo.photo_url}, "
//			+ "video_url = #{photo.video_url}, " + "phrase = #{photo.phrase}, "
//			+ "photo_pwd = #{photo.photo_pwd}, " + "design =  #{photo.design}, " + "publication = #{photo.publication}"
//			+ " where photo_seq = #{photo.photo_seq}")
//	public int modifyPhoto(@Param("photo") Photo photo);

    @Delete("delete from photo where photo_seq = #{photo_seq}")
    public int deletePhoto(@Param("photo_seq") int photo_seq);

    @Select("SELECT * FROM photo WHERE photo_seq >= 1 AND photo_seq <= 30")
    List<Photo> getSamplePhotoList();

//    @Insert("Insert INTO photo (  user_uid, photo_id, photo_date, photo_url, video_url, phrase, photo_pwd, design, publication) \n"
//            + "VALUES ( #{url})")
//    int insertPhoto2(@Param("url")String url);
}
