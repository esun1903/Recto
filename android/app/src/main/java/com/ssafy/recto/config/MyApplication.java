package com.ssafy.recto.config;

import android.app.Application;

import lombok.Data;

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

    public MyApplication getMyApplication() {
        return myApplication;
    }

    public void setMyApplication(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public Boolean getCardPublic() {
        return cardPublic;
    }

    public void setCardPublic(Boolean cardPublic) {
        this.cardPublic = cardPublic;
    }

    public Integer getCardDesign() {
        return cardDesign;
    }

    public void setCardDesign(Integer cardDesign) {
        this.cardDesign = cardDesign;
    }

    public String getCardVideo() {
        return cardVideo;
    }

    public void setCardVideo(String cardVideo) {
        this.cardVideo = cardVideo;
    }

    public String getCardPhoto() {
        return cardPhoto;
    }

    public void setCardPhoto(String cardPhoto) {
        this.cardPhoto = cardPhoto;
    }

    public String getCardPhrases() {
        return cardPhrases;
    }

    public void setCardPhrases(String cardPhrases) {
        this.cardPhrases = cardPhrases;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardDateNum() {
        return cardDateNum;
    }

    public void setCardDateNum(String cardDateNum) {
        this.cardDateNum = cardDateNum;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }

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