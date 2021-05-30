package com.ssafy.recto.controller;

import com.ssafy.recto.dto.Photo;
import com.ssafy.recto.service.PhotoService;
import com.ssafy.recto.service.S3FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/photo")
public class PhotoController {



    private static final Logger logger = LoggerFactory.getLogger(PhotoController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private PhotoService photoService;

    @Autowired
    private S3FileUploadService fileUploadService;

    @ApiOperation(value = "포토카드 ID로 검색", notes = "포토카드 ID로 검색", response = Photo.class)
    @GetMapping("/{photo_id}")
    public ResponseEntity<Photo> getPhoto(
            @PathVariable(value = "photo_id") String photo_id) throws Exception {
        logger.info("getPhoto - 호출");
        return new ResponseEntity<Photo>(photoService.getPhoto(photo_id), HttpStatus.OK);
    }

    @ApiOperation(value = "포토카드 목록", notes = "포토카드 목록을 반환한다.", response = Photo.class)
    @GetMapping("/list")
    public List<Photo> getPhotoList(
            @RequestParam("user_uid") @ApiParam(value = "회원 식별자", required = true) String user_uid) throws Exception {
        logger.info("getPhotoList - 호출");
        return photoService.getPhotoList(user_uid);
    }

    @ApiOperation(value = "포토카드 메인화면", notes = "포토카드 목록을 메인 화면에 반환한다.", response = Photo.class)
    @GetMapping("/main")
    public List<Photo> getPhotoMain(
            @RequestParam("user_uid") @ApiParam(value = "회원 식별자", required = true) String user_uid) throws Exception {
        logger.info("getPhotoMain - 호출");
        return photoService.getPhotoMain(user_uid);
    }

    @ApiOperation(value = "포토카드 정보 등록", notes = "포토카드 정보를 등록한다.", response = Photo.class)
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<String> insertPhoto(@RequestParam(value = "user_uid", required = false) String user_uid,
                                              @RequestParam(value = "design", required = false) int design,
                                              @RequestParam(value = "video", required = false) MultipartFile video,
                                              @RequestParam(value = "photo", required = false) MultipartFile photo,
                                              @RequestParam(value = "phrase", required = false) String phrase,
                                              @RequestParam(value = "photo_date", required = false) String photo_date,
                                              @RequestParam("photo_pwd") String photo_pwd) throws Exception {

        String videoUrl = "";
        String photoUrl = "";
        System.out.println(photo_date);
        String year = photo_date.substring(1, 5);
        String month = photo_date.substring(5, 7);
        String day = photo_date.substring(7, 9);
        String sum = year + "-" + month + "-" + day;
        System.out.println(sum);

        String str2 = year+month+year;
        LocalDate date = LocalDate.parse(sum, DateTimeFormatter.ISO_DATE);
        System.out.println(date);
        try {
            videoUrl = fileUploadService.upload(video);
            photoUrl = fileUploadService.upload(photo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        user_uid = user_uid.substring(1,user_uid.length()-1);
        phrase = phrase.substring(1,phrase.length()-1);
        photo_pwd = photo_pwd.substring(1,photo_pwd.length()-1);

        Photo photo2 = new Photo(user_uid, videoUrl,photo_date, photoUrl, phrase, photo_pwd, design);
        if (photoService.insertPhoto(photo2, date)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }


    @ApiOperation(value = "포토카드 샘플 목록", notes = "포토카드 샘플 목록을 반환한다.", response = Photo.class)
    @GetMapping("/samplePhotolist")
    public List<Photo> getPhotoList() throws Exception {
        logger.info("getSamplePhotoList - 호출");
        return photoService.getSamplePhotoList();
    }

//	@ApiOperation(value = "포토카드 정보 수정", notes = "포토카드 정보를 수정한다.", response = Photo.class)
//	@PutMapping
//	public ResponseEntity<String> modifyPhoto(@RequestBody Photo photo) throws Exception {
//		logger.info("updateQuestion - 호출");
//
//		if (photoService.modifyPhoto(photo)) {
//			return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
//		}
//
//		return new ResponseEntity<>(FAIL, HttpStatus.OK);
//	}

    @ApiOperation(value = "포토카드 삭제", notes = "포토카드 정보를 삭제한다.", response = Photo.class)
    @DeleteMapping
    public ResponseEntity<String> deletePhoto(@RequestParam("photo_seq") int photo_seq) throws Exception {
        logger.info("deleteQuestion - 호출");

        if (photoService.deletePhoto(photo_seq)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }

        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "포토카드 SEQ로 검색", notes = "포토카드 SEQ로 검색", response = Photo.class)
    @GetMapping
    public ResponseEntity<Photo> getPhotoDetail(
            @RequestParam(value= "photo_seq") int photo_seq) throws Exception {
        logger.info("getPhotoDetail - 호출");
        return new ResponseEntity<Photo>(photoService.getPhotoDetail(photo_seq), HttpStatus.OK);
    }

    @ApiOperation(value = "포토카드 비밀번호 일치여부", notes = "포토카드 비밀번호 일치여부", response = Photo.class)
    @GetMapping("/pwdCheck")
    public ResponseEntity<String> getPhotoPwd(
            @RequestParam(value= "photo_pwd") String photo_pwd, @RequestParam(value= "photo_seq") int photo_seq) throws Exception {
        logger.info("getPhotoPwd - 호출");
        Map<String, Object> map = new HashMap<>();
        map.put("photo_seq", photo_seq);
        map.put("photo_pwd", photo_pwd);

        if (photoService.getPhotoPwd(map)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }


}