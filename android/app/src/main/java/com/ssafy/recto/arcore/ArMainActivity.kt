package com.ssafy.recto.arcore

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.ssafy.recto.MainActivity
import com.ssafy.recto.R

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

}