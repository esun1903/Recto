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
        setContentView(R.layout.activity_scancard_ar)

        var intent = getIntent()
        var photoCode = intent.getStringExtra("photoCode")

        if (openGlVersion.toDouble() >= MIN_OPEN_GL_VERSION) {
            val bundle = Bundle()
            bundle.putString("photoCode", photoCode)
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val arVideoFragment = ArVideoFragment()
            arVideoFragment.arguments = bundle
            transaction.replace(R.id.fragmentContainer, arVideoFragment)
            transaction.commit()
//            supportFragmentManager.inTransaction { replace(R.id.fragmentContainer, ArVideoFragment())}
        } else {
            AlertDialog.Builder(this)
                    .setTitle("Device is not supported")
                    .setMessage("OpenGL ES 3.0 or higher is required. The device is running OpenGL ES $openGlVersion.")
                    .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                    .show()
        }

    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    companion object {
        private const val MIN_OPEN_GL_VERSION = 3.0
    }

}