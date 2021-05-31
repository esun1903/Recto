package com.ssafy.recto.user;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;        // Firebase 인증 처리
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;       // 로그인 입력 필드
    private SignInButton btn_google;         // 구글 로그인 버튼
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance(); // Firebase 인증 객체 초기화 - 유저 계정 정보 가져오기
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        // Shared Preferences 초기화
        sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        //sharedPreferences를 제어할 editor를 선언
        editor = sharedPreferences.edit();

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                // 이메일을 입력하지 않은 경우
                if (strEmail.equals("")) {
                    Toast.makeText(LoginActivity.this,"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호를 입력하지 않은 경우
                if (strPwd.equals("")) {
                    Toast.makeText(LoginActivity.this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 이메일 로그인 성공 시
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 로그인한 유저의 정보 가져오기
                            UserAccount account = new UserAccount();

                            // Shared Preferences에 emailID 저장
                            String emailId = account.setEmailId(firebaseUser.getEmail());
                            editor.putString("emailId", emailId);
                            editor.apply();
                            String checkEmail = sharedPreferences.getString("emailId", "");

                            // Shared Preferences에 idToken 저장
                            String userUid = account.setIdToken(firebaseUser.getUid()); // 로그인한 유저의 고유 Uid 가져오기
                            editor.putString("userUid", userUid);
                            editor.apply();
                            String checkUid = sharedPreferences.getString("userUid", "");

                            // Shared Preferences에 닉네임 저장
                            DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(userUid).child("nickname");
                            UserNickname.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String nickname = snapshot.getValue(String.class);
                                    editor.putString("nickname", nickname);
                                    editor.apply();
                                    String checkNickname = sharedPreferences.getString("nickname", "");

                                    // 로그인 성공 시 메인으로 이동
                                    if (nickname != null) {
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // 현재 액티비티 파괴
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인에 실패했습니다.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 화면으로 이동
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


        // 구글 로그인 연동
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id1)) // 서버와 연결되는 부분
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        btn_google = findViewById(R.id.btn_google);
        btn_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, REQ_SIGN_GOOGLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { // 구글 로그인 인증 요청 시 결과 값을 되돌려 받는 곳
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 구글 로그인 정보를 담고 있음 (이메일 주소, 닉네임, 프로필 사진 등)
                resultLogin(account); // 로그인 결과 값 출력 수행하라는 메소드
            }
        }
    }

    private void resultLogin(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // 구글 로그인 성공 시
                        if (task.isSuccessful()) {
                            // Shared Preferences에 emailID 저장
                            String emailId = account.getEmail();
                            editor.putString("emailId", emailId);
                            editor.apply();
                            String checkEmail = sharedPreferences.getString("emailId", "");

                            // Shared Preferences에 idToken 저장
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            String idToken = firebaseUser.getUid();
                            editor.putString("userUid", idToken);
                            editor.apply();
                            String checkUid = sharedPreferences.getString("userUid", "");

                            // Shared Preferences에 구글 닉네임 저장
                            String nickname = account.getDisplayName();
                            editor.putString("nickname", nickname);
                            editor.apply();
                            String checkNickname = sharedPreferences.getString("nickname", "");

                            // Database에 구글 로그인 사용자 관련 정보 insert
                            mDatabaseRef.child("UserAccount").child(idToken).child("emailId").setValue(emailId);
                            mDatabaseRef.child("UserAccount").child(idToken).child("idToken").setValue(idToken);
                            mDatabaseRef.child("UserAccount").child(idToken).child("nickname").setValue(nickname);

                            // 성공 토스트 메시지 출력
                            Toast.makeText(LoginActivity.this, "구글 로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();

                            // 메인 화면으로 이동
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "구글 로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}