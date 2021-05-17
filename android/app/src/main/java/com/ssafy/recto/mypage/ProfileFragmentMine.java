package com.ssafy.recto.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail2;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentMine extends Fragment {

    RecyclerView recyclerView;
    MainActivity mainActivity;
    ProfileFragmentMineAdapter profileFragmentMineAdapter;
    ApiInterface api;
    List<CardData> photoCards = new ArrayList<>();
    private View view;
    private GridLayoutManager mGridLayoutManager;
    private SharedPreferences sharedPreferences;
    String userId;
    int[] seq;
    int[] design_num;

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

    // 뷰페이저를 위한 인스탠스
    public static ProfileFragmentMine newInstance() {
        ProfileFragmentMine profileFragmentMine = new ProfileFragmentMine();
        return profileFragmentMine;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_mine, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // Shared Preferences 초기화
        sharedPreferences = this.getActivity().getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE);

        // user_uid 받아오기
        userId = sharedPreferences.getString("userUid", "");
        Log.e("유저 아이디 확인", userId);

        try {
            requestGet();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        
        recyclerView = view.findViewById(R.id.recyclerView);

        profileFragmentMineAdapter = new ProfileFragmentMineAdapter(getActivity(), photoCards);
        recyclerView.setAdapter(profileFragmentMineAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        ProfileFragmentMineAdapter.setOnItemClickListener(new ProfileFragmentMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos)
            {
                Log.d("확장형1 문구형2", String.valueOf(design_num[pos]));

                if (design_num[pos] == 1) {
                    // 상세 페이지로 photo_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ProfileFragmentMineDetail profileFragmentMineDetail = new ProfileFragmentMineDetail();
                    profileFragmentMineDetail.setArguments(bundle);
                    transaction.replace(R.id.main_frame, profileFragmentMineDetail);
                    transaction.commit();
                } else {
                    // 상세 페이지로 photo_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ProfileFragmentMineDetail2 profileFragmentMineDetail2 = new ProfileFragmentMineDetail2();
                    profileFragmentMineDetail2.setArguments(bundle);
                    transaction.replace(R.id.main_frame, profileFragmentMineDetail2);
                    transaction.commit();
                }

                // 밑에 코드 지우고 위에서 해줘야 seq값이 전달 됨
//                mainActivity.setFragment("profile_mine_detail");
            }
        });

        return view;
    }

    private void requestGet() throws ParseException{
        // user_uid 값으로 유저가 만든 카드 목록 불러오기
        Call<List<CardData>> call = api.getMineCard(userId);
        call.enqueue(new Callback<List<CardData>>() {
            @Override
            public void onResponse(Call<List<CardData>> call, Response<List<CardData>> response) {
                String uid, video, photo, phrase, date, pwd;
                int design;
                seq = new int[response.body().size()];
                design_num = new int[response.body().size()];

                photoCards.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    uid = response.body().get(i).getUser_uid();
                    design = response.body().get(i).getDesign();
                    video = response.body().get(i).getVideo_url();
                    photo = response.body().get(i).getPhoto_url();
                    phrase = response.body().get(i).getPhrase();
                    date = response.body().get(i).getPhoto_date();
                    pwd = response.body().get(i).getPhoto_pwd();

                    photoCards.add(new CardData(uid, design, video, photo, phrase, date, pwd));
//                    Log.d("api 잘 불러오나", String.valueOf(response.body().get(i)));
                    seq[i] = response.body().get(i).getPhoto_seq();
                    design_num[i] = design;
                }

                profileFragmentMineAdapter = new ProfileFragmentMineAdapter(getActivity(), photoCards);
                profileFragmentMineAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(profileFragmentMineAdapter);

            }

            @Override
            public void onFailure(Call<List<CardData>> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
