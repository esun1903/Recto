package com.ssafy.recto.home;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    MyApplication myApplication;
    private View view;
    private ArrayList<String> arrayList;
    private MyAdapter adapter;
    private RecyclerView listview;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
    private TextView tv_id;
    private View upline;

    int itemList[] = {R.drawable.free1, R.drawable.free2, R.drawable.free3,
            R.drawable.free4, R.drawable.free5, R.drawable.free6,
            R.drawable.free7, R.drawable.free8, R.drawable.free9,
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @Nullable
    @Override
    // Fragment가 처음 생성됐을 때 내부 구문 실행
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);
        myApplication = (MyApplication) getActivity().getApplication();
        init();
        return view;
    }

    private void init() {
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
        String userNick = myApplication.getUserNickname();
        Log.e("닉네임2", String.valueOf(userNick));
        if (userNick != null) {
            tv_id = view.findViewById(R.id.tv_id);
            tv_id.setText(userNick + "님의 Moment");
        }


//        if (myApplication.getGoogleNickname() != null) {
//
//            if (firebaseUser != null) {
//                Log.e("유저", "존재");
////                myApplication = new MyApplication();
//                myApplication = (MyApplication) getContext().getApplicationContext();
//                Log.e("이게 되나?", String.valueOf(myApplication));
//            }
//            Log.e("try문 진입 성공!", "제발 돌아라~");
////            myApplication = new MyApplication();
////            myApplication = (MyApplication) getContext().getApplicationContext();
////            myApplication = (MyApplication) getActivity().getApplication();
//            Log.e("이게 되나?1", String.valueOf(myApplication)); // 여기까지는 찍힙니다
////            String googleNickname = myApplication.getGoogleNickname();
//            String googleNickname = myApplication.getGoogleNickname();
////            Log.e("google", googleNickname);

//            if (!googleNickname.equals(null)) {
//                Log.e("google", googleNickname);
//                tv_id = view.findViewById(R.id.tv_id);
//                tv_id.setText(googleNickname + "님의 Moment");
//            }
//            else {
//                Log.e("else에", "왜 안 들어와?");
//                mFirebaseAuth = FirebaseAuth.getInstance(); // 유저 계정 정보 가져오기
//                mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기
////                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser(); // 로그인한 유저의 정보 가져오기
////                UserAccount account = new UserAccount();
//                String UserUid = account.setIdToken(firebaseUser.getUid()); // 로그인한 유저의 고유 Uid 가져오기
//                DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(UserUid).child("nickname");
//                //            Log.e("닉네임", String.valueOf(UserNickname));
//
//                UserNickname.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        tv_id = view.findViewById(R.id.tv_id);
//                        String nickname = snapshot.getValue(String.class);
//                        tv_id.setText(nickname + "님의 Moment");
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                    }
//                });
//            }
//        }
        else {
            Log.e("당신은", "null에 빠졌다,,");
            // 비 로그인 사용자를 위한 문구
            tv_id = view.findViewById(R.id.tv_id);
            tv_id.setText("당신의 Moment를 기록해보세요.");
        }

//        upline = view.findViewById(R.id.upline);
//        upline.getMeasuredWidth();

        listview = view.findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(linearLayoutManager);

        adapter = new MyAdapter(getActivity(), itemList, onClickItem);
        listview.setAdapter(adapter);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);
    }

    @Override
    // 중지되어 있던 Fragment가 재개/재실행 됐을 때 내부 구문 실행
    public void onResume() {
        super.onResume();
        Log.e("이게 되나?3-1", String.valueOf(myApplication));
        if (myApplication == null) {
            myApplication = (MyApplication) getActivity().getApplication();
        }
        Log.e("이게 되나?3-2", String.valueOf(myApplication));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("이게 되나?4-1", String.valueOf(myApplication));
        if (myApplication == null) {
            myApplication = (MyApplication) getActivity().getApplication();
        }
        Log.e("이게 되나?4-2", String.valueOf(myApplication));
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    };
}
