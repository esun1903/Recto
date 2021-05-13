package com.ssafy.recto.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.user.LoginActivity;

public class SplashActivity extends AppCompatActivity {

    MyApplication myApplication;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current = mFirebaseAuth.getCurrentUser();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기
        // 메인 화면 문구 - 로그인 사용자
        if (current != null) {
            myApplication = (MyApplication) getApplication();
        }
        else {
            myApplication = new MyApplication();
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                /* 스플래시 액티비티를 스택에서 제거. */
                SplashActivity.this.finish();
            }
        }, 2000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onBackPressed() {
        /* 스플래시 화면에서 뒤로가기 기능 제거. */
    }
}
