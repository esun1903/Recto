package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "Photo : 포토카드 정보")
public class Photo {

    @ApiModelProperty(value = "사진 seq")
    public int photo_seq;

    @ApiModelProperty(value = "사용자 seq")
    public int user_seq;

    @ApiModelProperty(value = "사진 식별 ID")
    public String photo_id;

    @ApiModelProperty(value = "사진 촬영 시간")
    public String photo_date;

    @ApiModelProperty(value = "사진 주소")
    public MultipartFile photo_str;

    @ApiModelProperty(value = "동영상 주소 ")
    public MultipartFile video_str;

    //DB에 넣어질 photo, video url
    @ApiModelProperty(value = "사진 주소")
    public String photo_url;

    @ApiModelProperty(value = "동영상 주소")
    public String video_url;

    @ApiModelProperty(value = "문구")
    public String phrase;

    @ApiModelProperty(value = "사진 비밀번호")
    public String photo_pwd;

    @ApiModelProperty(value = "포토카드 디자인")
    public int design;

    @ApiModelProperty(value = "공개/비공개")
    public boolean publication;


}
