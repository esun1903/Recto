package com.ssafy.recto.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import okhttp3.MultipartBody;

// photo 요청 데이터
@Data
public class CardData {

    @SerializedName("user_uid")
    @Expose
    String user_uid;

    @SerializedName("photo_seq")
    @Expose
    int photo_seq;

    @SerializedName("publication")
    @Expose
    boolean publication;

    @SerializedName("design")
    @Expose
    int design;

    @SerializedName("video_str")
    @Expose
    MultipartBody.Part video_str;

    @SerializedName("photo_str")
    @Expose
    MultipartBody.Part photo_str;

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

    public CardData(String user_uid, boolean publication, int design, String video_url, String photo_url, String phrase, String photo_date, String photo_pwd) {
        this.user_uid = user_uid;
        this.publication = publication;
        this.design = design;
        this.video_url = video_url;
        this.photo_url = photo_url;
        this.phrase = phrase;
        this.photo_date = photo_date;
        this.photo_pwd = photo_pwd;
    }

    public CardData(String user_uid, boolean publication, int design, MultipartBody.Part video_str, MultipartBody.Part photo_str, String phrase, String photo_date, String photo_pwd) {
        this.user_uid = user_uid;
        this.publication = publication;
        this.design = design;
        this.video_str = video_str;
        this.photo_str = photo_str;
        this.phrase = phrase;
        this.photo_date = photo_date;
        this.photo_pwd = photo_pwd;
    }
}