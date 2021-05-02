package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@ApiModel(value = "Present : 사진 선물")
public class Present {

    @ApiModelProperty(value = "사용자 seq")
    private int user_seq;
    @ApiModelProperty(value = "사진 seq")
    private int photo_seq;
    @ApiModelProperty(value = "사진 날짜")
    private LocalDateTime present_date;
    @ApiModelProperty(value = "사진 주소")
    private String photo_url;
    @ApiModelProperty(value = "제작자")
    private int photo_creator;

}
