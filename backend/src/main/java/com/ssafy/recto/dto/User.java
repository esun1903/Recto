package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "User : 유저 정보")
public class User {

	@ApiModelProperty(value = "유저 식별자")
	private int user_seq;
	@ApiModelProperty(value = "유저 ID")
	private String user_id;
	
}
