package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Match : 두 가지 사진 매칭")
public class Match {

    @ApiModelProperty(value = "사용자 seq")
    private int user_seq;
    @ApiModelProperty(value = "사진1 seq")
    private int photo1_seq;
    @ApiModelProperty(value = "사진2 seq")
    private int photo2_seq;
    @ApiModelProperty(value = "동영상 주소")
    private String video_url;


}
