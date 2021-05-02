package com.ssafy.recto.service;

import com.ssafy.recto.dao.PhotoDao;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.recto.dao.UserDao;
import com.ssafy.recto.dto.User;
import com.ssafy.recto.util.Sha256;

@Service
public class UserService {
	private final SqlSession sqlSession;
	private final UserDao userDao;
	@Autowired
	public UserService(SqlSession sqlSession,UserDao userDao) {
		this.sqlSession = sqlSession;
		this.userDao = userDao;
	}
	
	public User getUser(int userId) throws Exception{
		return userDao.getUser(userId);
	}
	
	public User userInfo(String user_id) throws Exception {
		return userDao.userInfo(user_id);
	}
	
	public boolean signUp(User user) throws Exception{
        String encryptPassword = Sha256.encrypt(user.getUser_pwd());
        user.setUser_pwd(encryptPassword);
        
        return userDao.signUp(user) == 1;
    }

    public boolean login(User user) throws Exception{
        String encryptPassword = Sha256.encrypt(user.getUser_pwd());
        user.setUser_pwd(encryptPassword);
        if(userDao.login(user)!=null){
			return true;
		}else{
        	return false;
		}
	}
	
}
