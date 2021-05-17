package com.ssafy.recto.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class GiftData {

    @SerializedName("gift_seq")
    @Expose
    private int gift_seq;
    @SerializedName("gift_from")
    @Expose
    private String gift_from;
    @SerializedName("photo_seq")
    @Expose
    private int photo_seq;
    @SerializedName("gift_to")
    @Expose
    private String gift_to;
    @SerializedName("photo_id")
    @Expose
    private String photo_id;
    @SerializedName("photo_url")
    @Expose
    private String photo_url;
    @SerializedName("video_url")
    @Expose
    private String video_url;
    @SerializedName("phrase")
    @Expose
    private String phrase;
    @SerializedName("photo_pwd")
    @Expose
    private String photo_pwd;
    @SerializedName("design")
    @Expose
    private int design;

    public GiftData(String gift_from, int photo_seq, String gift_to, String photo_id, String photo_url, String video_url, String phrase, String photo_pwd, int design) {
        this.gift_from = gift_from;
        this.photo_seq = photo_seq;
        this.gift_to = gift_to;
        this.photo_id = photo_id;
        this.photo_url = photo_url;
        this.video_url = video_url;
        this.phrase = phrase;
        this.photo_pwd = photo_pwd;
        this.design = design;
    }
}
