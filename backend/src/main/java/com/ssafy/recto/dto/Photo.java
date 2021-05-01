package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value = "Photo : 포토카드 정보")
public class Photo {

    @ApiModelProperty(value = "사진 seq")
    public static int photo_seq;
    @ApiModelProperty(value = "사용자 seq")
    public static int user_seq;
    @ApiModelProperty(value = "사진 식별 ID")
    public static String photo_id;
    @ApiModelProperty(value = "사진 촬영 시간")
    public static LocalDateTime photo_date;
    @ApiModelProperty(value = "사진 주소")
    public static String photo_url;
    @ApiModelProperty(value = "동영상 주소")
    public static String video_url;
    @ApiModelProperty(value = "문구")
    public static String pharse;
    @ApiModelProperty(value = "사진 비밀번호")
    public static String photo_pwd;
    @ApiModelProperty(value = "포토카드 디자인")
    public static int design;
    @ApiModelProperty(value = "공개/비공개")
    public static boolean close;

}
