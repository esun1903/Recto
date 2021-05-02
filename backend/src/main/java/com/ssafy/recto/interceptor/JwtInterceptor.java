package com.ssafy.recto.interceptor;

import com.ssafy.recto.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
	public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

	private static final String HEADER_AUTH = "access-token";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		final String token = request.getHeader(HEADER_AUTH);

		if (token != null && jwtService.isUsable(token)) {
			logger.info("토큰 사용 가능 : {}", token);
			//admin관련 요청 완료시 주석 해제할 것
			/*if (request.getRequestURI().substring(request.getContextPath().length()).startsWith("/admin")
				&& jwtService.getUserId().equals("admin")) {
				// admin권한이 필요한 페이지 요청시 사용자가 admin이면 true 반환
				return true;
			} else if (!request.getRequestURI().substring(request.getContextPath().length()).startsWith("/admin")) {
				// admin권한이 필요한 페이지 요청이 아니면 true 반환
				return true;
			} else {
				logger.info("권한 없음 : {}", token);
				throw new UnauthorizedException();
			}*/
			return true;
		} else {
			logger.info("토큰 사용 불가능 : {}", token);
			throw new AuthenticationException();
		}

	}
}
