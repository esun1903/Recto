package com.ssafy.recto.mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.GiftData;
import com.ssafy.recto.api.HttpClient;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentGiftDetail extends Fragment {

    ApiInterface api;
    TextView from_id;
    TextView gift_date;
    int seq;

    MainActivity mainActivity;
    ImageView giftImageView;
    ImageView info_dialog;
    Button gift_photo_card_list_btn;
    private View view;
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_gift_card_detail, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // 목록에서 gift_seq 값 (sep[pos]) 가져오기
        Bundle bundle = getArguments();
        if (bundle != null) {
            seq = bundle.getInt("seq");
        }

        try {
            requestGet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        from_id = view.findViewById(R.id.from_id);
        gift_date = view.findViewById(R.id.gift_date);

        giftImageView = view.findViewById(R.id.gift_image_detail);
        gift_photo_card_list_btn = view.findViewById(R.id.gift_photo_card_list_btn);
        info_dialog = view.findViewById(R.id.info_dialog);

        gift_photo_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("profile");
            }
        });

        info_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setIcon(R.drawable.question);
                ad.setTitle("Information");
                ad.setMessage("다운로드를 누르면 카드를 저장할 수 있습니다.");
                ad.show();
            }
        });

        return view;
    }

    public void requestGet() throws ParseException {
        // photo_seq 값으로 포토카드 검색
        Call<GiftData> call = api.getGift(seq);
        call.enqueue(new Callback<GiftData>() {
            @Override
            public void onResponse(Call<GiftData> call, Response<GiftData> response) {
                Log.d("카드 정보 가져오기", String.valueOf(response.body()));
                String from, date, url, to;
                int photo;

                from = response.body().getGift_from();
                date = response.body().getGift_date();
                photo = response.body().getPhoto_seq();
                url = response.body().getPhoto_url();
                to = response.body().getGift_to();

                // 아이디 넣어주기
                from_id.setText(from);
                gift_date.setText(date);

                Log.e("여기", from + " " + date + " " + url + " " + seq);

                Glide.with(getContext()).load(url).into(giftImageView);
            }

            @Override
            public void onFailure(Call<GiftData> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
