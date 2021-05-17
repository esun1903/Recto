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

import com.bumptech.glide.RequestManager;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.GiftData;
import com.ssafy.recto.api.HttpClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentGift extends Fragment {

    RecyclerView recyclerView;
    MainActivity mainActivity;
    ProfileFragmentGiftAdapter profileFragmentGiftAdapter;
    ApiInterface api;
    List<GiftData> photoGifts = new ArrayList<>();
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
    public static ProfileFragmentGift newInstance() {
        ProfileFragmentGift profileFragmentGift = new ProfileFragmentGift();
        return profileFragmentGift;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_gift, container, false);
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

        profileFragmentGiftAdapter = new ProfileFragmentGiftAdapter(getActivity(), photoGifts);
        recyclerView.setAdapter(profileFragmentGiftAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        ProfileFragmentGiftAdapter.setOnItemClickListener(new ProfileFragmentGiftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos)
            {
                Log.d("확장형1 문구형2", String.valueOf(design_num[pos]));

                if (design_num[pos] == 1) {
                    // 상세 페이지로 gift_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ProfileFragmentGiftDetail profileFragmentGiftDetail = new ProfileFragmentGiftDetail();
                    profileFragmentGiftDetail.setArguments(bundle);
                    transaction.replace(R.id.main_frame, profileFragmentGiftDetail);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    // 상세 페이지로 gift_seq 값 (sep[pos]) 보내주기
                    Bundle bundle = new Bundle(); // 데이터를 담을 번들
                    bundle.putInt("seq", seq[pos]);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ProfileFragmentGiftDetail2 profileFragmentGiftDetail2 = new ProfileFragmentGiftDetail2();
                    profileFragmentGiftDetail2.setArguments(bundle);
                    transaction.replace(R.id.main_frame, profileFragmentGiftDetail2);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }
        });

        return view;
    }

    public void requestGet()  throws ParseException {
        Call<List<GiftData>> call = api.getGiftList(userId);

        call.enqueue(new Callback<List<GiftData>>() {
            @Override
            public void onResponse(Call<List<GiftData>> call, Response<List<GiftData>> response) {
                String from, to, photo_id, photo_url, video_url, phrase, photo_pwd;
                int photo, design, gift_seq;
                seq = new int[response.body().size()];
                design_num = new int[response.body().size()];

                photoGifts.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    from = response.body().get(i).getGift_from();
                    photo = response.body().get(i).getPhoto_seq();
                    to = response.body().get(i).getGift_to();
                    photo_id = response.body().get(i).getPhoto_id();
                    photo_url = response.body().get(i).getPhoto_url();
                    video_url = response.body().get(i).getVideo_url();
                    phrase = response.body().get(i).getPhrase();
                    photo_pwd = response.body().get(i).getPhoto_pwd();
                    design = response.body().get(i).getDesign();

                    photoGifts.add(new GiftData(from, photo, to, photo_id, photo_url, video_url, phrase, photo_pwd, design));
                    Log.e("photo_seq", String.valueOf(response.body().get(i).getGift_seq()));
                    seq[i] = response.body().get(i).getGift_seq();
                    design_num[i] = design;
                }

                profileFragmentGiftAdapter = new ProfileFragmentGiftAdapter(getActivity(), photoGifts);
                profileFragmentGiftAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(profileFragmentGiftAdapter);

            }

            @Override
            public void onFailure(Call<List<GiftData>> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
