package com.ssafy.recto.controller;

import com.ssafy.recto.dto.Photo;
import com.ssafy.recto.service.PhotoService;
import com.ssafy.recto.service.UserService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/Insert/")
    public int PhotoInsert(@RequestBody Photo photo) {
        System.out.println("여기는 conroller야 !");
        System.out.println("사진이 들어왔다" + photo.photo_seq+" "+photo.user_seq+" "+photo.photo_pwd);
        int result = 0;
        HttpStatus status = null;
        try {
             result  = photoService.InsertPhoto(photo);
        } catch (Exception e) {
            logger.error("사진 등록 실패 : {}", e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return result;
    }


}