package com.ssafy.recto.arcore

import android.app.ActivityManager
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.SharedPreferences
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
            supportFragmentManager.inTransaction { replace(R.id.fragmentContainer, ArVideoFragment())}
        } else {
            AlertDialog.Builder(this)
                    .setTitle("Device is not supported")
                    .setMessage("OpenGL ES 3.0 or higher is required. The device is running OpenGL ES $openGlVersion.")
                    .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                    .show()
        }

        val gson = GsonBuilder().setLenient().create();
        val retrofit = Retrofit.Builder().baseUrl("http://k4a204.p.ssafy.io:8080/recto/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        val service = retrofit.create(PhotoService::class.java);

//        var sharedPreferences: SharedPreferences? = null
//        sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE)
//        val userUid: String? = sharedPreferences?.getString("userUid", "")

//        //getPhoto
//        service.getPhoto("204228419")?.enqueue(object : Callback<PhotoVO> {
//            override fun onFailure(call: Call<PhotoVO>?, t: Throwable?) {
//                Log.i("fail.TT", t.toString())
//            }

//            override fun onResponse(call: Call<PhotoVO>, response: Response<PhotoVO>) {
//                Log.d("Response :: ", response?.body().toString())
//                var gift_from = response.body()?.user_uid
//                var photo_seq = response.body()?.photo_seq
//                var gift_to = userUid
//
//                var gift = GiftVO(gift_from, photo_seq, gift_to)
//
//                //saveGift
//                service.saveGift(gift)?.enqueue(object : Callback<String> {
//                    override fun onFailure(call: Call<String>?, t: Throwable?) {
//                        Log.i("fail.TT", t.toString())
//                    }
//
//                    override fun onResponse(call: Call<String>, response: Response<String>) {
//                        Log.d("Response :: ", response?.body().toString())
//                    }
//                })
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