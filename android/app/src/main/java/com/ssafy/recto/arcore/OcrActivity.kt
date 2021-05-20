package com.ssafy.recto.arcore

import android.Manifest
import android.app.AlertDialog
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.text.TextBlock
import com.google.android.gms.vision.text.TextRecognizer
import com.google.gson.GsonBuilder
import com.ssafy.recto.MainActivity
import com.ssafy.recto.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_scancard_ocr.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.popup_scancard_pwd.*
import java.io.Serializable
import java.lang.Thread.sleep

class OcrActivity : AppCompatActivity() {

    private val MY_PERMISSIONS_REQUEST_CAMERA: Int = 101
    private lateinit var mCameraSource: CameraSource
    private lateinit var textRecognizer: TextRecognizer
    private var photoCode: String = ""
    private var photoCard = PhotoVO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scancard_ocr)
        requestForPermission()

        textRecognizer = TextRecognizer.Builder(this).build()
        if (!textRecognizer.isOperational) {
            Toast.makeText(this, "Dependencies are not loaded yet...please try after few moment!!", Toast.LENGTH_SHORT)
                    .show()
            Log.e("MainActivity", "Dependencies are downloading....try after few moment")
            return
        }

        mCameraSource = CameraSource.Builder(applicationContext, textRecognizer)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1280, 1024)
                .setAutoFocusEnabled(true)
                .setRequestedFps(2.0f)
                .build()

        surface_camera_preview.holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceDestroyed(holder: SurfaceHolder) {
                mCameraSource.stop()
            }

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    if (isCameraPermissionGranted()) {
                        mCameraSource.start(surface_camera_preview.holder)
                    } else {
                        requestForPermission()
                    }
                } catch (e: Exception) {
                    toast("Error:" + e.message)
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
            }
        })

        textRecognizer.setProcessor(object : Detector.Processor<TextBlock> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<TextBlock>) {
                val items = detections.detectedItems

                if (items.size() <= 0) {
                    return
                }

                tv.post {
                    val stringBuilder = StringBuilder()

                    for (i in 0 until items.size()) {
                        val item = items.valueAt(i)
                        if (item.value.isNumber()) {
                            if (item.value.length == 9) {
                                stringBuilder.append(item.value)
                                stringBuilder.append("\n")
                                photoCode = item.value
                                tv.text = stringBuilder.toString()
                            }
                        }
                    }
                }


            }
        })

        next_button.setOnClickListener {

            //비밀번호 유무를 체크해주기
            val gson = GsonBuilder().setLenient().create();
            val retrofit = Retrofit.Builder().baseUrl("http://k4a204.p.ssafy.io:8080/recto/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            val service = retrofit.create(PhotoService::class.java);
            Log.d("photocode", photoCode)
            //getPhoto
            service.getPhoto(photoCode)?.enqueue(object : Callback<PhotoVO> {
                override fun onFailure(call: Call<PhotoVO>?, t: Throwable?) {
                    Log.i("fail.TT", t.toString())
                }

                override fun onResponse(call: Call<PhotoVO>, response: Response<PhotoVO>) {
                    Log.d("Response :: ", response?.body().toString())
                    val photoPwd = response.body()?.photo_pwd
                    val d = response.body()?.design
                    val pd = response.body()?.photo_date
                    val pi = response.body()?.photo_id
                    val ps = response.body()?.photo_seq
                    val pu = response.body()?.photo_url
                    val vu = response.body()?.video_url
                    val uu = response.body()?.user_uid
                    val p = response.body()?.phrase
                    photoCard = PhotoVO(ps,uu,pi,pd,pu,vu,p,photoPwd,d)

                    if (pi == null || "".equals(pi) || "null".equals(pi)) {
                        toast("포토카드를 다시 인식해주세요")
                    } else if (photoPwd == null || "".equals(photoPwd) || "null".equals(photoPwd)) {
                        Log.d("photopassword", "없음") //비밀번호가 없으면?
                        change()
                    } else {
                        Log.d("photopassword", photoPwd.toString()) // 비밀번호가 있다면?
                        showPopup(photoPwd.toString())
                    }
                }
            })
        }
    }

    fun change() {
        mCameraSource.stop()
        var intent = Intent(this, ArMainActivity::class.java)
        intent.putExtra("photoCard", photoCard as Serializable)
        startActivity(intent)
    }

    private fun showPopup(photoPwd: String) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.popup_scancard_pwd, null)

        val alertDialog = AlertDialog.Builder(this@OcrActivity)
                .create()

        alertDialog.setView(view)
        alertDialog.show()

        val btn_cancel = view.findViewById<Button>(R.id.btn_cancel)
        btn_cancel.setOnClickListener {
            alertDialog.dismiss()
        }


        val btn_confirm = view.findViewById<Button>(R.id.btn_confirm)
        btn_confirm.setOnClickListener {
            val password = view.findViewById<EditText>(R.id.et_password)

            if(password.getText().toString().equals(photoPwd)){
                change()
            }
            else {
                toast("비밀번호가 틀렸습니다. 다시 입력해주세요.")
                password.setText("")
            }

        }
    }
    fun retry(view: View) {
        tv.setText("~ 인식 중 ~")
        sleep(3000)
    }

    fun String.isNumber(): Boolean {

        var i = 0
        while (i < this.length) {
            if (!('0' <= this[i] && this[i] <= '9'))
                return false
            i += 1
        }

        return true
    }

    private fun requestForPermission() {

        if (ContextCompat.checkSelfPermission(
                        this@OcrActivity,
                        Manifest.permission.CAMERA
                )
                != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this@OcrActivity,
                            Manifest.permission.CAMERA
                    )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                        this@OcrActivity,
                        arrayOf(
                                Manifest.permission.CAMERA
                        ),
                        MY_PERMISSIONS_REQUEST_CAMERA
                )
            }
        } else {
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
                this@OcrActivity,
                Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun toast(text: String) {
        Toast.makeText(this@OcrActivity, text, Toast.LENGTH_SHORT).show()
    }

    fun back_main(view: View) {
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)
    }



}