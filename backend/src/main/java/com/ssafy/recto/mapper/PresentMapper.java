package com.ssafy.recto.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.ssafy.recto.dto.Present;

@Mapper
public interface PresentMapper {

	@Insert("Insert INTO present ( photo_seq, user_seq, present_date, photo_url, photo_creator) \n"
			+ "VALUES ( #{present.photo_seq},#{present.user_seq},#{present.present_date},#{present.photo_url},#{present.photo_creator})")
	@Options(useGeneratedKeys = true)
	public int sendPresent(@Param("present") Present present);

	@Select("SELECT * FROM present WHERE photo_seq = #{photo_seq}")
	public Present getPresent(@Param("photo_seq") int photo_seq);
	
	@Select("SELECT * FROM present WHERE user_seq = #{user_seq}")
	public List<Present> getPresentList(@Param("user_seq") int user_seq);
}
