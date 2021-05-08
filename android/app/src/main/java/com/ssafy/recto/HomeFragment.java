package com.ssafy.recto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
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
        view = inflater.inflate(R.layout.home_fragment, container, false);
        init();
        return view;
    }

    private void init() {
        try {
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
                    tv_id = view.findViewById(R.id.tv_id);
                    String nickname = snapshot.getValue(String.class);
                    tv_id.setText(nickname + "님의 Moment");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        } catch (Exception e) {
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
