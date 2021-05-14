package com.ssafy.recto.api;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;



public interface ApiInterface {
    @GET("photo/samplePhotolist")
    Call<List<CardData>> getPublicCard();

    @GET("photo")
    Call<CardData> getCard(@Query("photo_seq") int photo_seq);

    @GET("photo/list")
    Call<List<CardData>> getMineCard(@Query("user_uid") String user_uid);

    Call<String> getUpdateProfileInfo(@Part MultipartBody.Part file, @PartMap Map<String, RequestBody> info);

    @Multipart
    @POST("photo")
    Call<String> requestCreateCard(@Part("user_uid") String user_uid, @Part("publication") boolean publication, @Part("design") int design, @Part  MultipartBody.Part video, @Part  MultipartBody.Part photo ,@Part("phrase") String phrase, @Part("photo_date") String photo_date, @Part("photo_pwd") String photo_pwd);

    @GET("gift/list")
    Call<List<GiftData>> getGiftList(@Query("gift_to") String gift_to);

    @GET("gift")
    Call<GiftData> getGift(@Query("gift_seq") int gift_seq);
}