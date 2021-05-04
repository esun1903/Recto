package com.ssafy.recto.controller;

import com.ssafy.recto.dto.Photo;
import com.ssafy.recto.dto.Present;
import com.ssafy.recto.service.PresentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("PresentController")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/present")
public class PresentController {

	private static final Logger logger = LoggerFactory.getLogger(PresentController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PresentService presentService;

	@ApiOperation(value = "선물 목록", notes = "선물 목록을 반환한다.", response = Present.class)
	@GetMapping("/list")
	public List<Present> getPresentList(
			@RequestParam("user_seq") @ApiParam(value = "회원 식별자", required = true) int user_seq) throws Exception {
		logger.info("getPresentList - 호출");
		return presentService.getPresentList(user_seq);
	}

	@ApiOperation(value = "선물 seq로 검색", notes = "선물 seq로 검색", response = Photo.class)
	@GetMapping
	public ResponseEntity<Present> getPresent(
			@RequestParam(value= "photo_seq") int photo_seq) throws Exception {
		logger.info("getPresent - 호출");
		return new ResponseEntity<Present>(presentService.getPresent(photo_seq), HttpStatus.OK);
	}
	
	@ApiOperation(value = "선물 등록", notes = "선물 정보를 등록한다.", response = Present.class)
	@PostMapping
	public ResponseEntity<String> sendPresent(@RequestBody @ApiParam(value = "선물 정보", required = true) Present present)
			throws Exception {
		logger.info("sendPresent - 호출");

		if (presentService.sendPresent(present)) {
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<>(FAIL, HttpStatus.OK);
	}

}