package com.ssafy.recto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("photo")
    Call<String> requestCreateCard(@Body ReqCreateCardData reqCreateCardData);
}