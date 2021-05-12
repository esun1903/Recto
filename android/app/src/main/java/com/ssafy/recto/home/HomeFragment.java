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
        myApplication = (MyApplication) getActivity().getApplication();
        view = inflater.inflate(R.layout.home_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current = mFirebaseAuth.getCurrentUser();
        // 메인 화면 문구 - 로그인 사용자
        if (current != null) {
            String userNick = myApplication.getUserNickname();
            Log.e("홈 닉네임", String.valueOf(userNick));
            tv_id = view.findViewById(R.id.tv_id);
            tv_id.setText(userNick + "님의 Moment");
        }

        // 메인 화면 문구 - 비 로그인 사용자
        else {
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

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    };
}
