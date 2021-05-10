package com.ssafy.recto.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import okhttp3.MultipartBody;

// photo 요청 데이터
public class Photo {

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

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public int getPhoto_seq() {
        return photo_seq;
    }

    public void setPhoto_seq(int photo_seq) {
        this.photo_seq = photo_seq;
    }

    public boolean isPublication() {
        return publication;
    }

    public void setPublication(boolean publication) {
        this.publication = publication;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        this.design = design;
    }

    public MultipartBody.Part getVideo_str() {
        return video_str;
    }

    public void setVideo_str(MultipartBody.Part video_str) {
        this.video_str = video_str;
    }

    public MultipartBody.Part getPhoto_str() {
        return photo_str;
    }

    public void setPhoto_str(MultipartBody.Part photo_str) {
        this.photo_str = photo_str;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhoto_date() {
        return photo_date;
    }

    public void setPhoto_date(String photo_date) {
        this.photo_date = photo_date;
    }

    public String getPhoto_pwd() {
        return photo_pwd;
    }

    public void setPhoto_pwd(String photo_pwd) {
        this.photo_pwd = photo_pwd;
    }

    //    public Photo(String user_uid, boolean publication, int design,String phrase, String photo_date, String photo_pwd) {
//        this.user_uid = user_uid;
//        this.publication = publication;
//        this.design = design;
//        this.phrase = phrase;
//        this.photo_date = photo_date;
//        this.photo_pwd = photo_pwd;
//    }
    public Photo(String user_uid, boolean publication, int design, MultipartBody.Part video_str, MultipartBody.Part photo_str, String phrase, String photo_date, String photo_pwd) {
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
