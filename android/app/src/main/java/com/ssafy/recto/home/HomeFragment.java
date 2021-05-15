package com.ssafy.recto.home;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail2;
import com.ssafy.recto.publiccard.PublicFragmentMyAdapter;
import com.ssafy.recto.user.UserAccount;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    MainActivity mainActivity;
    ApiInterface api;
    List<CardData> photoCards = new ArrayList<>();
    private View view;
    private MyAdapter adapter;
    private RecyclerView listview;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    private TextView tv_id;
    private View upline;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    int[] seq;
    int[] design_num;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    // Fragment가 처음 생성됐을 때 내부 구문 실행
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment, container, false);

        // User 관련
        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser current = mFirebaseAuth.getCurrentUser();
        // Shared Preferences 초기화
        sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);
        //sharedPreferences를 제어할 editor를 선언
        editor = sharedPreferences.edit();

        // api 연결
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // 카드뷰
        listview = view.findViewById(R.id.main_rv);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        listview.setLayoutManager(linearLayoutManager);

        adapter = new MyAdapter(getActivity(), photoCards);
        listview.setAdapter(adapter);

        MyListDecoration decoration = new MyListDecoration();
        listview.addItemDecoration(decoration);

        MyAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Log.d("확장형1 문구형2", String.valueOf(design_num[pos]));

                if (design_num[pos] == 1) {
                    // 상세 페이지로 photo_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    PublicFragmentCardDetail publicFragmentCardDetail = new PublicFragmentCardDetail();
                    publicFragmentCardDetail.setArguments(bundle);
                    transaction.replace(R.id.main_frame, publicFragmentCardDetail);
                    transaction.commit();
                } else {
                    // 상세 페이지로 photo_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    PublicFragmentCardDetail2 publicFragmentCardDetail2 = new PublicFragmentCardDetail2();
                    publicFragmentCardDetail2.setArguments(bundle);
                    transaction.replace(R.id.main_frame, publicFragmentCardDetail2);
                    transaction.commit();
                }
            }
        });

        // 로그인 사용자
        if (current != null) {
            // 메인 화면 문구
            String nickname = sharedPreferences.getString("nickname", "RECTO의 유저");
            Log.e("홈 닉네임 확인", nickname);
            tv_id = view.findViewById(R.id.tv_id);
            tv_id.setText(nickname + "님의 Moment");
        }

        // 비 로그인 사용자
        else {
            // 메인 화면 문구
            tv_id = view.findViewById(R.id.tv_id);
            tv_id.setText("당신의 Moment를 기록해보세요.");

            try {
                requestGet();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return view;
    }

    public void requestGet()  throws ParseException {
        Call<List<CardData>> call = api.getPublicCard();

        call.enqueue(new Callback<List<CardData>>() {
            @Override
            public void onResponse(Call<List<CardData>> call, Response<List<CardData>> response) {
                String uid, video, photo, phrase, date, pwd;
                boolean publication;
                int design;
                seq = new int[response.body().size()];
                design_num = new int[response.body().size()];

                photoCards.clear();
                // 메인 화면에 카드 다섯장만 불러오기
                for (int i = 0; i < 5; i++) {
                    uid = response.body().get(i).getUser_uid();
                    publication = response.body().get(i).isPublication();
                    design = response.body().get(i).getDesign();
                    video = response.body().get(i).getVideo_url();
                    photo = response.body().get(i).getPhoto_url();
                    phrase = response.body().get(i).getPhrase();
                    date = response.body().get(i).getPhoto_date();
                    pwd = response.body().get(i).getPhoto_pwd();

                    photoCards.add(new CardData(uid, publication, design, video, photo, phrase, date, pwd));
                    Log.e("photo_seq", String.valueOf(response.body().get(i).getPhoto_seq()));
                    seq[i] = response.body().get(i).getPhoto_seq();
                    design_num[i] = design;
                }

                adapter = new MyAdapter(getActivity(), photoCards);
                adapter.notifyDataSetChanged();
                listview.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CardData>> call, Throwable t) {
                Log.e("불러오기", "실패:<" + t.toString());
            }
        });
    }

    private View.OnClickListener onClickItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String str = (String) v.getTag();
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
        }
    };
}
