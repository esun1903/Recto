package com.ssafy.recto.service;



import com.ssafy.recto.dto.Gift;
import com.ssafy.recto.mapper.PhotoMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recto.mapper.GiftMapper;

import java.util.List;

@Service
public class GiftService {
    private final SqlSession sqlSession;

    @Autowired
    public GiftService(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public boolean saveGift(Gift gift){
        return sqlSession.getMapper(GiftMapper.class).saveGift(gift) == 1;
    }

    public Gift getGift(int gift_seq) throws Exception{
        return sqlSession.getMapper(GiftMapper.class).getGift(gift_seq);
    }

    public List<Gift> getGiftList(String gift_to) throws Exception{
        return sqlSession.getMapper(GiftMapper.class).getGiftList(gift_to);
    }

    public boolean deleteGift(int gift_seq) throws Exception {
        return sqlSession.getMapper(GiftMapper.class).deleteGift(gift_seq) == 1 ;
    }
}
