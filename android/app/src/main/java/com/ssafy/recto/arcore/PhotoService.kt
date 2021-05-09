package com.ssafy.recto.arcore

import retrofit2.Call
import retrofit2.http.*

interface PhotoService {

    @GET("photo/list")
    fun getPhotoList(@Query("user_seq") user_seq : Int) : Call<List<PhotoVO>>

    @GET("photo/{photo_id}")
    fun getPhoto(@Path("photo_id") photo_id : String) : Call<PhotoVO>

    @POST("photo")
    fun insertPhoto(@Body photo : PhotoVO) : Call<String>
}