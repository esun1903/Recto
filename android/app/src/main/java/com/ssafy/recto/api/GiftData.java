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
    @SerializedName("gift_date")
    @Expose
    private String gift_date;
    @SerializedName("photo_url")
    @Expose
    private String photo_url;
    @SerializedName("gift_to")
    @Expose
    private String gift_to;

    public GiftData(String gift_from, int photo_seq, String gift_date, String photo_url, String gift_to) {
        this.gift_from = gift_from;
        this.photo_seq = photo_seq;
        this.gift_date = gift_date;
        this.photo_url = photo_url;
        this.gift_to = gift_to;
    }
}
