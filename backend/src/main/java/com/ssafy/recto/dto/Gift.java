package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;


@Data
@ApiModel(value = "Gift : 사진 선물")
public class Gift {


    @ApiModelProperty(value = "선물 seq")
    private int gift_seq;
    @ApiModelProperty(value = "발신인 seq")
    private String gift_from;
    @ApiModelProperty(value = "사진 seq")
    private int photo_seq;
    @ApiModelProperty(value = "선물한 날짜")
    private LocalDate gift_date;
    @ApiModelProperty(value = "사진 주소")
    private String photo_url;
    @ApiModelProperty(value = "수신인 seq")
    private String gift_to;

}
