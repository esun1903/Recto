package com.ssafy.recto.controller;

import io.swagger.annotations.Api;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.recto.dto.User;
import com.ssafy.recto.service.JwtService;
import com.ssafy.recto.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api("UserController")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@RequestMapping("/user")
public class UserController {
//
//	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//	private static final String SUCCESS = "success";
//	private static final String FAIL = "fail";
//
//	@Autowired
//	private JwtService jwtService;
//	@Autowired
//	private UserService userService;
//
//	@ApiOperation(value = "회원 정보", notes = "회원 정보를 반환한다.", response = User.class)
//	@GetMapping
//	public ResponseEntity<User> getUser(@RequestParam("user_seq") @ApiParam(value = "회원 seq", required = true) int user_seq) throws Exception {
//		logger.info("getUser - 호출");
//		return new ResponseEntity<User>(userService.getUser(user_seq), HttpStatus.OK);
//	}
//
//	@ApiOperation(value = "회원 인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
//	@GetMapping("/{user_id}")
//	public ResponseEntity<Map<String, Object>> getInfo(
//		@PathVariable("user_id") @ApiParam(value = "인증할 회원 ID.", required = true) String user_id, HttpServletRequest request) {
//		logger.info("getInfo - 호출");
//		Map<String, Object> resultMap = new HashMap<>();
//
//		if (jwtService.isUsable(request.getHeader("access-token"))) {
//			logger.info("사용 가능한 토큰!!!");
//			try {
//				// 로그인 사용자 정보.
//User user = userService.userInfo(user_id);
//				resultMap.put("userInfo", user);
//				resultMap.put("message", SUCCESS);
//
//				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//			} catch (Exception e) {
//				logger.error("정보조회 실패 : {}", e);
//				resultMap.put("message", e.getMessage());
//
//				return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} else {
//			logger.error("사용 불가능 토큰!!!");
//			resultMap.put("message", FAIL);
//
//			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//		}
//
//	}
//
//    @ApiOperation(value = "회원가입", notes = "회원 정보를 등록한다.", response = User.class)
//    @PostMapping
//    public ResponseEntity<String> signUp(@RequestBody @ApiParam(value = "회원 정보", required = true) User user) throws Exception {
//        logger.info("signUp - 호출");
//
//        if(userService.signUp(user)) {
//            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(FAIL, HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "로그인", notes = "로그인", response = User.class)
//    @PostMapping("/login")
//    public ResponseEntity<Map<String, Object>> login(@RequestBody @ApiParam(value = "회원 정보", required = true) User user) throws Exception {
//        logger.info("login - 호출");
//
//        Map<String, Object> resultMap = new HashMap<>();
//		if (userService.login(user)) {
//			String token = jwtService.create("user_id", user.getUser_id(), "access-token");// key, data, subject
//			logger.info("로그인 토큰정보 : {}", token);
//			resultMap.put("access-token", token);
//			resultMap.put("message", SUCCESS);
//
//			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
//		}
//		else {
//			resultMap.put("message", FAIL);
//			System.out.println(userService.login(user));
//			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//    }
//
		}