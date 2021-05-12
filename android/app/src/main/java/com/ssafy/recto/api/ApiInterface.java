package com.ssafy.recto.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @POST("photo")
    Call<String> requestCreateCard(@Body CardData cardData);

    @GET("photo/samplePhotolist")
    Call<List<CardData>> getPublicCard();

    @GET("photo")
    Call<CardData> getCard(@Query("photo_seq") int photo_seq);
    //springboot에서 응답을 받는 값              //여기는 우리가 주는값
}