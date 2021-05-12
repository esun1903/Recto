package com.ssafy.recto.publiccard;

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
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicFragmentCardDetail2 extends Fragment {

    ApiInterface api;
    MainActivity mainActivity;
    ImageView cardImageView;
    ImageView info_dialog;
    Button free_photo_card_list_btn;
    TextView tv_phrases;
    TextView card_id;
    private View view;
    private Context mContext;
    int seq;
    String photo_url;

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
        view = inflater.inflate(R.layout.public_fragment_card_detail2, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // 카드 목록에서 photo_seq 값 (sep[pos]) 가져오기
        Bundle bundle = getArguments();
        if (bundle != null) {
            seq = bundle.getInt("seq");
        }
        Log.d("seq 값을 가져올까", String.valueOf(seq));

        try {
            requestGet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_phrases = view.findViewById(R.id.tv_phrases);
        card_id = view.findViewById(R.id.card_id);
        cardImageView = view.findViewById(R.id.card_image_detail);
        free_photo_card_list_btn = view.findViewById(R.id.free_photo_card_list_btn);
        info_dialog = view.findViewById(R.id.info_dialog);

//        Log.d("photo url 가져오는지 확인", photo_url);

        free_photo_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("public");
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
        Call<CardData> call = api.getCard(seq);
        call.enqueue(new Callback<CardData>() {
            @Override
            public void onResponse(Call<CardData> call, Response<CardData> response) {
                Log.d("카드 정보 가져오기", String.valueOf(response.body()));
                String uid, id, video, photo, phrase, date, pwd, url;
                boolean publication;
                int photo_seq, design;

                id = response.body().getPhoto_id();
                phrase = response.body().getPhrase();

//                Log.d("아이디랑 어쩌구", id + " "+ phrase+ " "+ response.body().toString());

                photo_url = response.body().getPhoto_url();

                // 문구 넣어주기
                tv_phrases.setText(phrase);
//                Log.d("문구", String.valueOf(phrase));

                // 아이디 넣어주기
                card_id.setText(id);

                // 이미지 불러오기
                Glide.with(getContext()).load(photo_url).into(cardImageView);
            }

            @Override
            public void onFailure(Call<CardData> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
