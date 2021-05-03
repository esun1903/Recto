package com.ssafy.recto.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ssafy.recto.exception.UnauthorizedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtService {

	public static final Logger logger = LoggerFactory.getLogger(JwtService.class);

	private static final String TK = "rectoSecret";
	private static final int EXPIRE_MINUTES = 60;

	public <T> String create(String key, T data, String subject) {
		String jwt = Jwts.builder().setHeaderParam("typ", "JWT")
			.setHeaderParam("regDate", System.currentTimeMillis())
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * EXPIRE_MINUTES))
			.setSubject(subject)
			.claim(key, data)
			.signWith(SignatureAlgorithm.HS256, this.generateKey()).compact();
		return jwt;
	}

	private byte[] generateKey() {
		byte[] key = null;
		try {
			key = TK.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			if (logger.isInfoEnabled()) {
				e.printStackTrace();
			} else {
				logger.error("Making JWT Key Error ::: {}", e.getMessage());
			}
		}

		return key;
	}

	//	전달 받은 토큰이 제대로 생성된것인지 확인 하고 문제가 있다면 UnauthorizedException을 발생.
	public boolean isUsable(String jwt) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(this.generateKey()).parseClaimsJws(jwt);
			return true;
		} catch (Exception e) {
			//			if (logger.isInfoEnabled()) {
			//				e.printStackTrace();
			//			} else {
			logger.error(e.getMessage());
			//			}
			//			throw new UnauthorizedException();
			//			개발환경
			return false;
		}
	}

	public Map<String, Object> get(String key) {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
			.getRequest();
		String jwt = request.getHeader("access-token");
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey(TK.getBytes("UTF-8")).parseClaimsJws(jwt);
		} catch (Exception e) {
			//			if (logger.isInfoEnabled()) {
			//				e.printStackTrace();
			//			} else {
			logger.error(e.getMessage());
			//			}
			throw new UnauthorizedException();
			//			개발환경
			//			Map<String,Object> testMap = new HashMap<>();
			//			testMap.put("userid", userid);
			//			return testMap;
		}
		Map<String, Object> value = claims.getBody();
		logger.info("value : {}", value);
		return value;
	}

	public String getUserId() {
		return (String)this.get("user").get("user_id");
	}
}
