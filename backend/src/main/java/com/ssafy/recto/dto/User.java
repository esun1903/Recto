package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "User : 유저 정보")
public class User {

	@ApiModelProperty(value = "사용자 seq")
	private int user_seq;
	@ApiModelProperty(value = "사용자 id")
	private String user_id;
	@ApiModelProperty(value = "사용자 비밀번호")
	private String user_pwd;
	@ApiModelProperty(value = "사용자 닉네임")
	private String user_nickname;
	@ApiModelProperty(value = "관리자 / 유저")
	private String manager;
	@ApiModelProperty(value = "사용자 프로필사진")
	private String user_image;
}
