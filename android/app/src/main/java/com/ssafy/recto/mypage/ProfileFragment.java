package com.ssafy.recto.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
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
import com.ssafy.recto.user.UserAccount;

public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    ViewPager viewPager;
    TabLayout tabLayout;
    MyApplication myApplication;
    private View view;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef; // 실시간 데이터베이스
    private TextView tv_hello;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment, container, false);

        // 뷰페이저 세팅
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tab_layout);

        // getActivity().getSupportFragmentManager()를 쓰면 에러가 나므로 getChildFragmentManager()를 써줘야 함
        fragmentPagerAdapter = new ProfileViewPagerAdapter(getChildFragmentManager());

        viewPager.setAdapter(fragmentPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        mFirebaseAuth = FirebaseAuth.getInstance(); // 유저 계정 정보 가져오기
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기
        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 로그인한 유저의 정보 가져오기
        UserAccount account = new UserAccount();
        String UserUid = account.setIdToken(firebaseUser.getUid()); // 로그인한 유저의 고유 Uid 가져오기
        DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(UserUid).child("nickname");
//            Log.e("닉네임", String.valueOf(UserNickname));

        UserNickname.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_hello = view.findViewById(R.id.tv_hello);
                String nickname = snapshot.getValue(String.class);
                tv_hello.setText(nickname + "님, 반갑습니다.");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // 로그아웃 버튼
        Button btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 하기
                mFirebaseAuth.signOut();
                Log.e("로그", "아웃");

                myApplication = null;
                Log.e("초기화", "합니다");
//                myApplication.setUserUid("null");
//                myApplication.setUserNickname("null");

                // 로그아웃 후 Home Fragment로 이동
                mainActivity.setFragment("home");
            }
        });

        return view;
    }
}
