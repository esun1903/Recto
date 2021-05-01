package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "Match : 사진과 사진의 Match")
public class Match {

    @ApiModelProperty(value = "사용자 seq")
    private int user_seq;
    @ApiModelProperty(value = "사진1 seq")
    private int photo1_seq;
    @ApiModelProperty(value = "사진2 seq")
    private int photo2_seq;
    @ApiModelProperty(value = "사진주소")
    private String photo_url;


}
