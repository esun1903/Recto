package com.ssafy.recto.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("photo")
    Call<String> requestCreateCard(@Body CardData cardData);
}