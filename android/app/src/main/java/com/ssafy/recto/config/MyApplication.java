package com.ssafy.recto.config;

import android.app.Application;

import lombok.Data;

@Data
public class MyApplication extends Application {
    private Boolean cardPublic;
    private Integer cardDesign;
    private String cardVideo;
    private String cardPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardDateNum;
    private String cardPassword;
}