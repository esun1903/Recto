package com.ssafy.recto.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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
import com.ssafy.recto.config.MyApplication;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseAuth mFirebaseAuth;        // Firebase 인증 처리
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd;       // 로그인 입력 필드
    private SignInButton btn_google;         // 구글 로그인 버튼
    private GoogleApiClient googleApiClient; // 구글 API 클라이언트 객체
    private GoogleSignInClient mGoogleSignInClient;
    private static final int REQ_SIGN_GOOGLE = 100; // 구글 로그인 결과 코드
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance(); // Firebase 인증 객체 초기화
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);

        // 데이터가 있다면 myApplication을 매번 새로 불러오지 않는다!
        // 없을 때만 한 번 불러온다..?
        if (myApplication == null) {
            myApplication = (MyApplication) getApplication();
        }

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그인 요청
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // 이메일 로그인 성공 시
                        if (task.isSuccessful()) {
//                            mFirebaseAuth = FirebaseAuth.getInstance(); // 유저 계정 정보 가져오기
//                            mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 로그인한 유저의 정보 가져오기
                            UserAccount account = new UserAccount();
                            String UserUid = account.setIdToken(firebaseUser.getUid()); // 로그인한 유저의 고유 Uid 가져오기
//                            Log.e("UID 확인", UserUid);
                            myApplication.setUserUid(UserUid);
                            Log.e("잘 불러와졌나?", myApplication.getUserUid());
                            DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(UserUid).child("nickname");
//                            Log.e("닉네임 확인", String.valueOf(UserNickname));

                            UserNickname.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String nickname = snapshot.getValue(String.class);
                                    Log.e("닉네임 저장", String.valueOf(nickname));
                                    myApplication.setUserNickname(nickname);
                                    Log.e("잘 불러와졌나?", myApplication.getUserNickname());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });


//                            // My Application에 닉네임 저장
//                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 로그인한 유저의 정보 가져오기
//                            UserAccount account = new UserAccount();
//                            String UserUid = account.setIdToken(firebaseUser.getUid()); // 로그인한 유저의 고유 Uid 가져오기
//                            DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(UserUid).child("nickname");
//                            UserNickname.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    String nickname = snapshot.getValue(String.class);
//                                    myApplication.setUserNickname(String.valueOf(UserNickname));
//                                    Log.e("닉네임 잘 들어갔는지 확인", myApplication.getUserNickname());
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });

                            // 로그인 성공 시 메인으로 이동
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인에 실패했습니다!", Toast.LENGTH_LONG).show();
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
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

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
                            // My Application에 emailID 저장
                            String emailId = account.getEmail();
                            myApplication.setUserEmail(emailId);
                            Log.e("이메일 잘 들어갔는지 확인", myApplication.getUserEmail());

                            // My Application에 idToken 저장
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            String idToken = firebaseUser.getUid();
                            myApplication.setUserUid(idToken);
                            Log.e("UID 잘 들어갔는지 확인", myApplication.getUserUid());

                            // My Application에 구글 닉네임 저장
                            String googleNickname = account.getDisplayName();
                            myApplication.setUserNickname(googleNickname);
                            Log.e("닉네임 잘 들어갔는지 확인", myApplication.getUserNickname());

                            // Database에 구글 로그인 사용자 관련 정보 insert
                            mDatabaseRef.child("UserAccount").child(idToken).child("emailId").setValue(emailId);
                            mDatabaseRef.child("UserAccount").child(idToken).child("idToken").setValue(idToken);
                            mDatabaseRef.child("UserAccount").child(idToken).child("nickname").setValue(googleNickname);

                            // 성공 토스트 메시지 출력
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                            // 메인 화면으로 이동
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}