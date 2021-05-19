package com.ssafy.recto.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;        // Firebase 인증 처리
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
    private EditText mEtEmail, mEtPwd, mEtConfirmPwd, mEtNickname;     // 회원가입 입력 필드
    private CheckBox chk_privacy;            // 개인정보처리방침 동의 버튼
    private Button mBtnRegister;            // 회원가입 버튼
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    // 이메일 형식 체크
    public static boolean isEmail(String email){
        boolean returnValue = false;
        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }

    // 비밀번호 유효성 체크
    public static boolean passwordValidate(String password) {
        boolean returnValue = false;
        // 최소 8자리, 최소 하나의 문자와 하나의 숫자 포함
        String val = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        Pattern p = Pattern.compile(val);
        Matcher m = p.matcher(password);
        if(m.matches()){
            returnValue = true;
        }
        return returnValue;
    }

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
        chk_privacy = findViewById(R.id.chk_privacy);
        mBtnRegister = findViewById(R.id.btn_register);

        // Shared Preferences 초기화
        sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        //sharedPreferences를 제어할 editor를 선언
        editor = sharedPreferences.edit();

        chk_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chk_privacy.isChecked()) {
                    Log.e("동의", "했다");
                    // 다이얼로그
                    AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                    ad.setIcon(R.drawable.recto_logo);
                    ad.setTitle("개인정보처리방침");
                    ad.setMessage(getString(R.string.privacy_text));

                    // 닫기 버튼
                    ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    ad.show();
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 회원가입 처리 시작
                // getText()로 사용자가 입력한 값 가져오기
                String strEmail = mEtEmail.getText().toString();
                String strPwd = mEtPwd.getText().toString();
                String strConfirmPwd = mEtConfirmPwd.getText().toString();
                String strNickmame = mEtNickname.getText().toString();

                // 회원가입 유효성 검사
                // 이메일을 입력하지 않은 경우
                if (strEmail.equals("")) {
                    Toast.makeText(RegisterActivity.this,"이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 이메일 형식이 틀린 경우
                if (!isEmail(strEmail)) {
                    Toast.makeText(RegisterActivity.this,"이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호를 입력하지 않은 경우
                if (strPwd.equals("")) {
                    Toast.makeText(RegisterActivity.this,"비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 조건을 만족하지 못하는 경우
                if (!passwordValidate(strPwd)) {
                    Toast.makeText(RegisterActivity.this,"비밀번호는 최소 8자리, 문자와 숫자를 최소 하나씩 포함해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

               // 비밀번호 확인을 입력하지 않은 경우
                if (strConfirmPwd.equals("")) {
                    Toast.makeText(RegisterActivity.this,"비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호와 비밀번호 확인이 불일치하는 경우
                if (!strPwd.equals(strConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this,"비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 닉네임을 입력하지 않은 경우
                if (strNickmame.equals("")) {
                    Toast.makeText(RegisterActivity.this,"닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 개인정보처리방침에 동의하지 않은 경우
                if (!chk_privacy.isChecked()) {
                    Toast.makeText(RegisterActivity.this,"개인정보처리방침에 동의해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Firebase Auth를 통한 인증 처리 절차 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        // 회원가입 성공 - 정상 처리
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

                            // Shared Preferences에 emailID, idToken, 닉네임 저장
                            editor.putString("emailId", firebaseUser.getEmail());
                            editor.putString("userUid", firebaseUser.getUid());
                            editor.putString("nickname", strNickmame);
                            editor.apply();

                            // 성공 토스트 메시지 출력
                            Toast.makeText(RegisterActivity.this, "RECTO의 회원이 되신 걸 환영합니다!", Toast.LENGTH_SHORT).show();

                            // 회원가입 성공 시 자동 로그인 - 메인 화면으로 이동
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish(); // 현재 액티비티 파괴
                        }
                        // 회원가입 실패 - 이미 동일한 계정이 존재하는 경우 등
                        else {
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}