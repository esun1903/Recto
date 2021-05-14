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

    private View view;
    private GridLayoutManager mGridLayoutManager;

    ApiInterface api;
    List<GiftData> photoGifts = new ArrayList<>();

    int[] seq;

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
                // 상세 페이지로 gift_seq 값 (sep[pos]) 보내주기
                Bundle bundle = new Bundle(); // 데이터를 담을 번들
                bundle.putInt("seq", seq[pos]);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ProfileFragmentGiftDetail profileFragmentGiftDetail = new ProfileFragmentGiftDetail();
                profileFragmentGiftDetail.setArguments(bundle);
                transaction.replace(R.id.main_frame, profileFragmentGiftDetail);
                transaction.commit();

            }
        });

        return view;
    }

    public void requestGet()  throws ParseException {
        Call<List<GiftData>> call = api.getGiftList("1");

        call.enqueue(new Callback<List<GiftData>>() {
            @Override
            public void onResponse(Call<List<GiftData>> call, Response<List<GiftData>> response) {
                String from, date, url, to;
                int photo;
                seq = new int[response.body().size()];

                photoGifts.clear();
                for (int i = 0; i < response.body().size(); i++) {
                    from = response.body().get(i).getGift_from();
                    photo = response.body().get(i).getPhoto_seq();
                    url = response.body().get(i).getPhoto_url();
                    date = response.body().get(i).getGift_date();
                    to = response.body().get(i).getGift_to();

                    photoGifts.add(new GiftData(from, photo, date, url, to));
                    Log.e("photo_seq", String.valueOf(response.body().get(i).getGift_seq()));
                    seq[i] = response.body().get(i).getGift_seq();
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
