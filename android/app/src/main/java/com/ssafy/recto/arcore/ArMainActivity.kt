package com.ssafy.recto.arcore

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.gson.GsonBuilder
import com.ssafy.recto.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArMainActivity : AppCompatActivity() {

    private val openGlVersion by lazy {
        (getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
            .deviceConfigurationInfo
            .glEsVersion
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_ar)

        if (openGlVersion.toDouble() >= MIN_OPEN_GL_VERSION) {
            supportFragmentManager.inTransaction { replace(R.id.fragmentContainer, ArVideoFragment()) }
        } else {
            AlertDialog.Builder(this)
                .setTitle("Device is not supported")
                .setMessage("OpenGL ES 3.0 or higher is required. The device is running OpenGL ES $openGlVersion.")
                .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                .show()
        }


//        val gson = GsonBuilder().setLenient().create();
//        val retrofit = Retrofit.Builder().baseUrl("http://k4a204.p.ssafy.io:8080/recto/")
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build();
//        val service = retrofit.create(PhotoService::class.java);
//        var gift = GiftVO()

        //getPhoto
//        service.getPhoto("204228419")?.enqueue(object : Callback<PhotoVO> {
//            override fun onFailure(call: Call<PhotoVO>?, t: Throwable?) {
//                Log.i("fail.TT", t.toString())
//            }
//            override fun onResponse(call: Call<PhotoVO>, response: Response<PhotoVO>) {
//                Log.d("Response :: ", response?.body().toString())
//                var gift_from = response.body()?.user_seq
//                var photo_seq = response.body()?.photo_seq
//                var gift_to = "GGRXTUnHCqZZQP27XKxxRkgdjtR2"
//                var photo_id = response.body()?.photo_id
//                var photo_url = response.body()?.photo_url
//                var video_url = response.body()?.video_url
//                var phrase = response.body()?.phrase
//                var photo_pwd = response.body()?.photo_pwd
//                var design = response.body()?.design
//                var publication = response.body()?.publication
//
//                gift = GiftVO(gift_from, photo_seq, gift_to, photo_id, photo_url, video_url, phrase, photo_pwd, design, publication)
//                Log.d("gift1", gift.toString())
//            }
//        })
//
//        //saveGift
//        service.saveGift(gift)?.enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>?, t: Throwable?) {
//                Log.i("fail.TT", t.toString())
//            }
//            override fun onResponse(call: Call<String>, response: Response<String>) {
//                Log.d("Response :: ", response?.body().toString())
//                Log.d("gift2", gift.toString())
//            }
//        })

        //getPhotoList
//        service.getPhotoList(1)?.enqueue(object : Callback<List<PhotoVO>> {
//            override fun onFailure(call: Call<List<PhotoVO>>?, t: Throwable?) {
//                Log.i("fail.TT", t.toString())
//            }
//            override fun onResponse(call: Call<List<PhotoVO>>, response: Response<List<PhotoVO>>) {
//                Log.d("Response :: ", response?.body().toString())
//                var data : List<PhotoVO>? = response?.body()
//                for ( i in data!!){
//                    Log.i("data" , i.toString())
//                }
//            }
//        })

    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    companion object {
        private const val MIN_OPEN_GL_VERSION = 3.0
    }
}