package com.ssafy.recto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment_6_Success_phrases extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    ApiInterface api;
    private View view;
    private Button btn_previous;
    private Button btn_next;
    private TextView tv_date;
    private TextView tv_phrases;
    private ImageView iv_photo;
    private Boolean cardPrivate;
    private Integer cardDesign;
    private String cardVideo;
    private String cardPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardPassword;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myApp = (MyApplication) getActivity().getApplication();
        view = inflater.inflate(R.layout.create_fragment_6_success_phrases, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        tv_date = view.findViewById(R.id.tv_date);
        tv_phrases = view.findViewById(R.id.tv_phrases);
        iv_photo = view.findViewById(R.id.iv_photo);

        tv_date.setText(myApp.getCardDate());
        tv_phrases.setText(myApp.getCardPhrases());

        cardPrivate = myApp.getCardPrivate();
        cardDesign = myApp.getCardDesign();
        cardVideo = myApp.getCardVideo();
        cardPhoto = myApp.getCardPhoto();
        cardPhrases = myApp.getCardPhrases();
        cardDate = myApp.getCardDate();
        cardPassword = myApp.getCardPassword();

//        Log.e("private", String.valueOf(cardPrivate));
//        Log.e("des", String.valueOf(cardDesign));
//        Log.e("vi", cardVideo);
//        Log.e("ph", cardPhoto);
//        Log.e("pr", cardPhrases);
//        Log.e("da", cardDate);
//        Log.e("pwd", cardPassword);

        try {
            String imgpath = getActivity().getCacheDir() + "/photo";   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            iv_photo.setImageBitmap(bm);
        } catch (Exception e) {
            Toast.makeText(getContext(), "사진 로드 실패", Toast.LENGTH_SHORT).show();
        }

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardPrivate && (cardDesign == 1)) {
                    mainActivity.setFragment("create_writeinfo_pron");
                } else if (cardPrivate && (cardDesign == 2)) {
                    mainActivity.setFragment("create_writeinfo_prph");
                } else if (!cardPrivate && (cardDesign == 1)) {
                    mainActivity.setFragment("create_writeinfo_puon");
                } else if (!cardPrivate && (cardDesign == 2)) {
                    mainActivity.setFragment("create_writeinfo_puph");
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    requestPost();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                // mainActivity.setFragment("home");
            }
        });

        return view;
    }

    public void requestPost() throws ParseException {
        //  cardVideo, cardPhoto
        //  ReqCreateCardData reqCreateCardData = new ReqCreateCardData(1, cardPrivate, cardDesign, "/video", "/photo", cardPhrases, "20200203", cardPassword, "500000001", 501);
        ReqCreateCardData reqCreateCardData = new ReqCreateCardData(1, false, 1, "/video", "/photo", "galinjisunda", "20200203", "2580", "500000015", 515);

        Call<String> call = api.requestCreateCard(reqCreateCardData);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("success", "success yeeeeee :>" + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t);
            }
        });
    }

}