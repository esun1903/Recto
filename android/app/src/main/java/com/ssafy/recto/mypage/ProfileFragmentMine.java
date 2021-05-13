package com.ssafy.recto.mypage;

import android.content.Context;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;

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
    String userId = "1";

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

//    int[] images = {R.drawable.user1,R.drawable.user2, R.drawable.user3,
//            R.drawable.user4, R.drawable.user5, R.drawable.user1,R.drawable.user2, R.drawable.user3,
//            R.drawable.user4, R.drawable.user5 };

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_mine, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );
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
                Log.i("this is", String.valueOf(pos));
                mainActivity.setFragment("profile_mine_detail");
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
                boolean publication;
                int design;

                photoCards.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    uid = response.body().get(i).getUser_uid();
                    publication = response.body().get(i).isPublication();
                    design = response.body().get(i).getDesign();
                    video = response.body().get(i).getVideo_url();
                    photo = response.body().get(i).getPhoto_url();
                    phrase = response.body().get(i).getPhrase();
                    date = response.body().get(i).getPhoto_date();
                    pwd = response.body().get(i).getPhoto_pwd();

                    photoCards.add(new CardData(uid, publication, design, video, photo, phrase, date, pwd));
                    Log.d("api 잘 불러오나", String.valueOf(response.body().get(i)));
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
