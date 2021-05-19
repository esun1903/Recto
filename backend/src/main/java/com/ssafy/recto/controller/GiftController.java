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
import org.springframework.web.bind.annotation.*;

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
            @RequestParam("gift_to") @ApiParam(value = "수신인 식별자", required = true) String gift_to) throws Exception {
        logger.info("getGiftList - 호출");
        return giftService.getGiftList(gift_to);
    }

    @ApiOperation(value = "선물 seq로 검색", notes = "선물 seq로 검색", response = Gift.class)
    @GetMapping
    public ResponseEntity<Gift> getGift(
            @RequestParam(value= "gift_seq") int gift_seq) throws Exception {
        logger.info("getGift - 호출");
        return new ResponseEntity<Gift>( giftService.getGift(gift_seq), HttpStatus.OK);
    }

    @ApiOperation(value = "선물 id로 검색", notes = "선물 id로 검색", response = String.class)
    @GetMapping("/{photo_id}")
    public ResponseEntity<String> checkGift(
            @PathVariable(value= "photo_id") String photo_id, @RequestParam(value= "user_uid") String user_uid) throws Exception {
        logger.info("checkGift - 호출");
        if (giftService.checkGift(photo_id, user_uid)){
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "선물 저장", notes = "선물 정보를 저장한다.", response = String.class)
    @PostMapping
    public ResponseEntity<String> saveGift(@RequestBody @ApiParam(value = "선물 정보", required = true) Gift gift)
            throws Exception {
        logger.info("saveGift - 호출");

        if (giftService.saveGift(gift)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }
        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

    @ApiOperation(value = "선물 삭제", notes = "선물 정보를 삭제한다.", response = String.class)
    @DeleteMapping
    public ResponseEntity<String> deleteGift(@RequestParam("gift_seq") int gift_seq) throws Exception {
        logger.info("deleteGift - 호출");

        if (giftService.deleteGift(gift_seq)) {
            return new ResponseEntity<>(SUCCESS, HttpStatus.OK);
        }

        return new ResponseEntity<>(FAIL, HttpStatus.OK);
    }

}