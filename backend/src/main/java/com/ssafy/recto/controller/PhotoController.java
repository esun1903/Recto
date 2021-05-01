package com.ssafy.recto.controller;

import com.ssafy.recto.dto.Photo;
import com.ssafy.recto.dto.User;
import com.ssafy.recto.service.PhotoService;
import com.ssafy.recto.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api("PhotoController")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/photo")
public class PhotoController {

	private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private PhotoService photoService;

	@PostMapping("/Insert")
	public int PhotoInsert(@RequestBody Photo photo) {
		System.out.println("여기는 conroller야 !");
		System.out.println("사진이 들어왔다" + photo.photo_seq + " " + photo.user_seq + " " + photo.photo_pwd);
		int result = 0;
		HttpStatus status = null;
		try {
			result = photoService.InsertPhoto(photo);
		} catch (Exception e) {
			logger.error("사진 등록 실패 : {}", e);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return result;
	}

	@ApiOperation(value = "포토카드 정보", notes = "포토카드 정보를 반환한다.", response = Photo.class)
	@GetMapping
	public ResponseEntity<Photo> getPhoto(
			@RequestParam("photo_seq") @ApiParam(value = "포토카드 식별자", required = true) int photo_seq) throws Exception {
		logger.info("getPhoto - 호출");
		return new ResponseEntity<Photo>(photoService.getPhoto(photo_seq), HttpStatus.OK);
	}
	
	@ApiOperation(value = "포토카드 목록", notes = "포토카드 목록을 반환한다.", response = Photo.class)
	@GetMapping("/list")
	public ResponseEntity<Photo> getPhotoList(
			@RequestParam("user_seq") @ApiParam(value = "회원 식별자", required = true) int user_seq) throws Exception {
		logger.info("getPhotoList - 호출");
		return new ResponseEntity<Photo>(photoService.getPhotoList(user_seq), HttpStatus.OK);
	}

	@ApiOperation(value = "포토카드 정보 등록", notes = "포토카드 정보를 등록한다.", response = Photo.class)
	@PostMapping
	public ResponseEntity<String> insertPhoto(@RequestBody @ApiParam(value = "포토카드 정보", required = true) Photo photo)
			throws Exception {
		logger.info("insertPhoto - 호출");

		if (photoService.insertPhoto(photo)) {
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "포토카드 정보 수정", notes = "포토카드 정보를 수정한다.", response = Photo.class)
	@PutMapping
	public ResponseEntity<String> modifyPhoto(@RequestBody Photo photo) throws Exception {
		logger.info("updateQuestion - 호출");

		if (photoService.modifyPhoto(photo)) {
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<>(FAIL, HttpStatus.OK);
	}

	@ApiOperation(value = "포토카드 삭제", notes = "포토카드 정보를 삭제한다.", response = Photo.class)
	@DeleteMapping
	public ResponseEntity<String> deletePhoto(@RequestParam("photo_seq") int photo_seq) throws Exception {
		logger.info("deleteQuestion - 호출");

		if (photoService.deletePhoto(photo_seq)) {
			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
		}

		return new ResponseEntity<>(FAIL, HttpStatus.OK);
	}

}