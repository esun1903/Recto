package com.ssafy.recto.arcore

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ssafy.recto.MainActivity
import com.ssafy.recto.R
import kotlinx.android.synthetic.main.popup_ar_tip.*


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
        var photoCard = intent.getSerializableExtra("photoCard")

        if (openGlVersion.toDouble() >= MIN_OPEN_GL_VERSION) {
            val bundle = Bundle()
            bundle.putSerializable("photoCard", photoCard)
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            val arVideoFragment = ArVideoFragment()
            arVideoFragment.arguments = bundle
            transaction.replace(R.id.fragmentContainer, arVideoFragment)
            transaction.commit()
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

    fun back(view: View) {
        val nextIntent = Intent(this, MainActivity::class.java)
        startActivity(nextIntent)
    }

    fun showTip(view: View) {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view = inflater.inflate(R.layout.popup_ar_tip, null)

        val alertDialog = android.app.AlertDialog.Builder(this@ArMainActivity)
                .create()

        alertDialog.setView(view)
        alertDialog.setCancelable(false)
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        alertDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        alertDialog.show()

        val iv_next = view.findViewById<ImageView>(R.id.iv_next)
        val iv_image = view.findViewById<ImageView>(R.id.iv_image)
        val tv_tip = view.findViewById<TextView>(R.id.tv_tip)
        println(iv_next.getTag())
        iv_next.setOnClickListener {
            println(iv_next.getTag())
            when(iv_next.getTag()) {
                "next" -> {
                    iv_next.setImageResource(R.drawable.popup_prev)
                    iv_next.setTag("prev")
                    iv_image.setImageResource(R.drawable.popup_two)
                    tv_tip.setText("1. 원활한 인식을 위해 카드를 천천히 좌우로 움직여 스캔해주세요.\n" +
                            "2. 인식에 성공하면 화면이 잠시 멈춘 뒤 영상이 재생됩니다.\n" +
                            "* 스마트폰 카메라의 초점이 흐릴 경우, 스토어에서 Google AR core를 업데이트해주세요.")
                }
                "prev" -> {
                    iv_next.setImageResource(R.drawable.popup_next)
                    iv_next.setTag("next")
                    iv_image.setImageResource(R.drawable.popup_one)
                    tv_tip.setText("1. AR포토카드는 깔끔한 배경에서 빛 반사를 피해 스캔해주세요.\n" +
                            "2. 카드 전체가 보이는 위치에서 인식해주세요.")
                }
                else -> {
                }
            }
        }

        val iv_cancel = view.findViewById<ImageView>(R.id.iv_cancel)
        iv_cancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }

}