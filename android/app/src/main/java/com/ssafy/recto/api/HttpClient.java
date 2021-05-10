package com.ssafy.recto.api;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpClient {
    private static Retrofit retrofit;

    // Http 통신을 위한 Retrofit 객체반환
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Retrofit getRetrofit() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
//                       builder.baseUrl("http://k4a204.p.ssafy.io:8080/recto/");
            builder.baseUrl("http://10.0.2.2:8080/recto/");
            builder.addConverterFactory(GsonConverterFactory.create(gson));  // 받아오는 Json 구조의 데이터를 객체 형태로 변환

            retrofit = builder.build();
        }

        return retrofit;
    }
//    private static Retrofit retrofit;
//
//    // Http 통신을 위한 Retrofit 객체반환
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    public static Retrofit getRetrofit() {
//
//    Gson gson = new GsonBuilder()
//        .setLenient()
//        .create();
//
//        if(retrofit == null) {
//            Retrofit.Builder builder = new Retrofit.Builder();
// //           builder.baseUrl("http://k4a204.p.ssafy.io:8080/recto/");
//            builder.baseUrl("http://10.0.2.2:8080/recto/");
//            builder.addConverterFactory(GsonConverterFactory.create(gson));  // 받아오는 Json 구조의 데이터를 객체 형태로 변환
//
//            retrofit = builder.build();
//        }
//
//        return retrofit;
//    }
}