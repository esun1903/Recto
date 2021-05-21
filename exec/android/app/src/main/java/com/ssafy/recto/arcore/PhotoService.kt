package com.ssafy.recto.arcore

import retrofit2.Call
import retrofit2.http.*

interface PhotoService {

    @GET("photo/list")
    fun getPhotoList(@Query("user_seq") user_seq: Int) : Call<List<PhotoVO>>

    @GET("photo/{photo_id}")
    fun getPhoto(@Path("photo_id") photo_id: String) : Call<PhotoVO>

    @GET("gift/{photo_id}")
    fun checkPhoto(@Path("photo_id") photo_id: String, @Query("user_uid") user_uid: String) : Call<String>

    @POST("gift")
    fun saveGift(@Body gift: GiftVO): Call<String>
}