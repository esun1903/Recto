package com.ssafy.recto.dto;

import lombok.Data;

@Data
public class ErrorMessage {

    private final String message;
    private final Integer status;

}