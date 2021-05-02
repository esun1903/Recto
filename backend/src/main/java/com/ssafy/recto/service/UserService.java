package com.ssafy.recto.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recto.dao.UserDao;
import com.ssafy.recto.dto.User;
import com.ssafy.recto.util.Sha256;

@Service
public class UserService {
	private final SqlSession sqlSession;
	
	@Autowired
	public UserService(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	public User getUser(int userId) throws Exception{
		return sqlSession.getMapper(UserDao.class).getUser(userId);
	}
	
	public User userInfo(String user_id) throws Exception {
		return sqlSession.getMapper(UserDao.class).userInfo(user_id);
	}
	
	public boolean signUp(User user) throws Exception{
        String encryptPassword = Sha256.encrypt(user.getUser_pwd());
        user.setUser_pwd(encryptPassword);
        
        return sqlSession.getMapper(UserDao.class).signUp(user) == 1;
    }

    public boolean login(User user) throws Exception{
        String encryptPassword = Sha256.encrypt(user.getUser_pwd());
        user.setUser_pwd(encryptPassword);
        
        return sqlSession.getMapper(UserDao.class).login(user) == 1;
	}
	
}
