package com.ssafy.recto;

import android.app.Application;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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