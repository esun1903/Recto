package com.ssafy.recto.mapper;

import java.util.List;

import com.ssafy.recto.dto.Gift;
import org.apache.ibatis.annotations.*;


@Mapper
public interface GiftMapper {

	@Insert("Insert INTO Gift ( photo_seq, photo_from, gift_date, photo_url, photo_to) \n"
			+ "VALUES ( #{Gift.photo_seq},#{Gift.photo_from},now(),#{Gift.photo_url},#{Gift.photo_to})")
	@Options(useGeneratedKeys = true)
	public int sendGift(@Param("Gift") Gift Gift);

	@Select("SELECT * FROM Gift WHERE photo_seq = #{photo_seq}")
	public Gift getGift(@Param("photo_seq") int photo_seq);
	
	@Select("SELECT * FROM Gift WHERE photo_from = #{photo_from}")
	public List<Gift> getGiftList(@Param("photo_from") int photo_from);
}
