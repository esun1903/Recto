package com.ssafy.recto.config;

import android.app.Application;

import lombok.Data;

@Data
public class MyApplication extends Application {
    private MyApplication myApplication = null;
    private Boolean cardPublic;
    private Integer cardDesign;
    private String cardVideo;
    private String cardPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardDateNum;
    private String cardPassword;
    private String userNickname;
    private String googleNickname;

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getGoogleNickname() {
        return googleNickname;
    }

    public void setGoogleNickname(String googleNickname) {
        this.googleNickname = googleNickname;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = (MyApplication) getApplicationContext();
    }
}