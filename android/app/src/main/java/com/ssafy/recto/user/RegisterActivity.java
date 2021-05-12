package com.ssafy.recto.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ssafy.recto.R;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;        // Firebase 인증 처리
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtConfirmPwd, mEtNickname;     // 회원가입 입력 필드
    private Button mBtnRegister;            // 회원가입 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto");

        mEtEmail = findViewById(R.id.et_email);
        mEtPwd = findViewById(R.id.et_pwd);
        mEtConfirmPwd = findViewById(R.id.et_confirmpwd);
        mEtNickname = findViewById(R.id.et_nickname);
        mBtnRegister = findViewById(R.id.btn_register);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리 시작
                // getText()로 사용자가 입력한 값 가져오기
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strConfirmPwd = mEtConfirmPwd.getText().toString();
                String strNickmame = mEtNickname.getText().toString();

                // 비밀번호와 비밀번호 확인이 불일치하는 경우
                if (!strPwd.equals(strConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
//                    Log.e("비밀번호 일치 여부", "불일치!");
                    return;
                }

                // Firebase Auth를 통한 인증 처리 절차 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // 정상 처리 (가입 성공 시)
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            // 개인정보 보호를 위해 Pwd와 Confirm Pwd는 DB에 저장하지 않는다.
//                            account.setPassword(strPwd);
//                            account.setConfirmpwd(strConfirmPwd);
                            account.setNickname(strNickmame);

                            // setValue: Database에 insert (삽입)
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            // 성공 토스트 메시지 출력
                            Toast.makeText(RegisterActivity.this, "RECTO의 회원이 되신 걸 환영합니다!", Toast.LENGTH_SHORT).show();

                            // 회원가입 성공 시 로그인 화면으로 이동
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        } else {
                            Toast.makeText(RegisterActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}