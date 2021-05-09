package com.ssafy.recto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;

// photo 요청 데이터
@Data
@AllArgsConstructor
public class ReqCreateCardData {

    @SerializedName("user_seq")
    @Expose
    int user_seq;

    @SerializedName("publication")
    @Expose
    boolean publication;

    @SerializedName("design")
    @Expose
    int design;

    @SerializedName("video_url")
    @Expose
    String video_url;

    @SerializedName("photo_url")
    @Expose
    String photo_url;

    @SerializedName("phrase")
    @Expose
    String phrase;

    @SerializedName("photo_date")
    @Expose
    String photo_date;

    @SerializedName("photo_pwd")
    @Expose
    String photo_pwd;

}
