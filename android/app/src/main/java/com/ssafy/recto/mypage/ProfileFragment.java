package com.ssafy.recto.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.ssafy.recto.user.UserAccount;

import java.util.Map;

public class ProfileFragment extends Fragment {

    MainActivity mainActivity;
    ViewPager viewPager;
    TabLayout tabLayout;
    private View view;
    private FragmentPagerAdapter fragmentPagerAdapter;
    private FirebaseAuth mFirebaseAuth;
    private TextView tv_hello;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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

        mFirebaseAuth = FirebaseAuth.getInstance();

        // Shared Preferences 초기화
        sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        //sharedPreferences를 제어할 editor를 선언
        editor = sharedPreferences.edit();

        // Greeting 문구
        String nickname = sharedPreferences.getString("nickname", "RECTO 유저");
        Log.e("프로필 닉네임 확인", nickname);
        tv_hello = view.findViewById(R.id.tv_hello);
        tv_hello.setText(nickname + "님, 반갑습니다.");

        // 로그아웃 버튼
        Button btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 로그아웃 하기
                mFirebaseAuth.signOut();

                // Shared Preferences 초기화
                editor.clear();
                editor.apply();

                // 로그아웃 후 Home Fragment로 이동
                mainActivity.setFragment("home");
            }
        });

        return view;
    }
}
