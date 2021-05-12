package com.ssafy.recto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "Photo : 포토카드 정보")
public class Photo {

    @ApiModelProperty(value = "사진 seq")
    public int photo_seq;

    @ApiModelProperty(value = "사용자 uid")
    public String user_uid;

    @ApiModelProperty(value = "사진 식별 ID")
    public String photo_id;

    @ApiModelProperty(value = "사진 촬영 시간")
    public String photo_date;

    public MultipartFile photo_str;

    public MultipartFile video_str;

    //DB에 넣어질 photo, video url
    @ApiModelProperty(value = "사진 주소")
    public String photo_url;

    @ApiModelProperty(value = "동영상 주소")
    public String video_url;

    @ApiModelProperty(value = "문구")
    public String phrase;

    @ApiModelProperty(value = "사진 비밀번호")
    public String photo_pwd;

    @ApiModelProperty(value = "포토카드 디자인")
    public int design;

    @ApiModelProperty(value = "공개/비공개")
    public boolean publication;

    public MultipartFile getPhoto_str() {
        return photo_str;
    }

    public MultipartFile getVideo_str() {
        return video_str;
    }

    public String getPhoto_url() {
        return photo_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setPhoto_str(MultipartFile photo_str) {
        this.photo_str = photo_str;
    }

    public void setVideo_str(MultipartFile video_str) {
        this.video_str = video_str;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public int getPhoto_seq() {
        return photo_seq;
    }

    public void setPhoto_seq(int photo_seq) {
        this.photo_seq = photo_seq;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getPhoto_date() {
        return photo_date;
    }

    public void setPhoto_date(String photo_date) {
        this.photo_date = photo_date;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhoto_pwd() {
        return photo_pwd;
    }

    public void setPhoto_pwd(String photo_pwd) {
        this.photo_pwd = photo_pwd;
    }

    public int getDesign() {
        return design;
    }

    public void setDesign(int design) {
        this.design = design;
    }

    public boolean isPublication() {
        return publication;
    }

    public void setPublication(boolean publication) {
        this.publication = publication;
    }
}
