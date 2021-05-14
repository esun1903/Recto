package com.ssafy.recto.mapper;

import java.util.List;

import com.ssafy.recto.dto.Gift;
import org.apache.ibatis.annotations.*;


@Mapper
public interface GiftMapper {

	@Insert("Insert INTO gift ( photo_seq, gift_from, gift_date, photo_url, gift_to) \n"
			+ "VALUES ( #{Gift.photo_seq},#{Gift.gift_from},now(),#{Gift.photo_url},#{Gift.gift_to})")
	@Options(useGeneratedKeys = true)
	public int sendGift(@Param("Gift") Gift Gift);

	@Select("SELECT * FROM gift WHERE gift_seq = #{gift_seq}")
	public Gift getGift(@Param("gift_seq") int gift_seq);
	
	@Select("SELECT * FROM gift WHERE gift_to = #{gift_to}")
	public List<Gift> getGiftList(@Param("gift_to") String gift_to);
}
