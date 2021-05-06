package com.ssafy.recto.controller;

import com.ssafy.recto.dto.Gift;
import com.ssafy.recto.dto.Photo;

import com.ssafy.recto.service.GiftService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("GiftController")
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/gift")
public class GiftController {

    private static final Logger logger = LoggerFactory.getLogger(GiftController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private GiftService giftService;

    @ApiOperation(value = "선물 목록", notes = "선물 목록을 반환한다.", response = Gift.class)
    @GetMapping("/list")
    public List<Gift> getGiftList(
            @RequestParam("photo_from") @ApiParam(value = "회원 식별자", required = true) int photo_from) throws Exception {
        logger.info("getGiftList - 호출");
        return giftService.getGiftList(photo_from);
    }

    @ApiOperation(value = "선물 seq로 검색", notes = "선물 seq로 검색", response = Photo.class)
    @GetMapping
    public ResponseEntity<Gift> getGift(
            @RequestParam(value= "photo_seq") int photo_seq) throws Exception {
        logger.info("getGift - 호출");
        return new ResponseEntity<Gift>( giftService.getGift(photo_seq), HttpStatus.OK);
    }

    @ApiOperation(value = "선물 등록", notes = "선물 정보를 등록한다.", response = Gift.class)
    @PostMapping
    public ResponseEntity<String> sendGift(@RequestBody @ApiParam(value = "선물 정보", required = true) Gift gift)
            throws Exception {
        logger.info("sendGift - 호출");

        if (giftService.sendGift(gift)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

}