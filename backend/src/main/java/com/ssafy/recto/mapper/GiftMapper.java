package com.ssafy.recto.mapper;

import java.util.List;

import com.ssafy.recto.dto.Gift;
import org.apache.ibatis.annotations.*;


@Mapper
public interface GiftMapper {

	@Insert("Insert INTO gift ( photo_seq, gift_from, gift_to) \n"
			+ "VALUES ( #{Gift.photo_seq},#{Gift.gift_from},#{Gift.gift_to})")
	@Options(useGeneratedKeys = true)
	public int saveGift(@Param("Gift") Gift Gift);

	@Select("SELECT * FROM photo, gift WHERE photo.photo_seq = gift.photo_seq and gift_seq = #{gift_seq}")
	public Gift getGift(@Param("gift_seq") int gift_seq);

	@Select("SELECT count(*) FROM photo, gift WHERE photo.photo_seq = gift.photo_seq and photo_id = #{photo_id}")
	public int checkGift(@Param("photo_id") String photo_id);
	
	@Select("SELECT * FROM photo, gift WHERE photo.photo_seq = gift.photo_seq and gift_to = #{gift_to} order by gift_seq desc")
	public List<Gift> getGiftList(@Param("gift_to") String gift_to);

	@Delete("delete from gift where gift_seq = #{gift_seq}")
	public int deleteGift(@Param("gift_seq") int gift_seq);

}
