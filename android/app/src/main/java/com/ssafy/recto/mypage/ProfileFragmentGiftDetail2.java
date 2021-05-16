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

public class ProfileFragmentGiftDetail2 extends Fragment {

    ApiInterface api;
    TextView from_id;
    TextView card_id;
    TextView tv_phrases;
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
        view = inflater.inflate(R.layout.profile_fragment_gift_card_detail2, container, false);
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
        card_id = view.findViewById(R.id.card_id);
        tv_phrases = view.findViewById(R.id.tv_phrases);
        giftImageView = view.findViewById(R.id.card_image_detail);
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
                String from, to, photo_id, photo_url, video_url, phrase, photo_pwd;
                int photo, design, gift_seq;
                boolean publication;

                from = response.body().getGift_from();
                photo_url = response.body().getPhoto_url();
                photo_id = response.body().getPhoto_id();
                phrase = response.body().getPhrase();

                // 보낸 사람 아이디 넣어주기
                from_id.setText("from." + from);

                // 카드 아이디 넣어주기
                card_id.setText(photo_id);

                // 문구 넣어주기
                tv_phrases.setText(phrase);

                Glide.with(getContext()).load(photo_url).into(giftImageView);
            }

            @Override
            public void onFailure(Call<GiftData> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
