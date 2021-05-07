package com.ssafy.recto;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateFragment_6_Success_onlyphoto extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private Button btn_previous;
    private Button btn_next;
    private ImageView iv_photo;
    private TextView tv_date;

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

        myApp = (MyApplication) getActivity().getApplication();
        view = inflater.inflate(R.layout.create_fragment_6_success_onlyphoto, container, false);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        tv_date = view.findViewById(R.id.tv_date);
        iv_photo = view.findViewById(R.id.iv_photo);

        tv_date.setText(myApp.getCardDate());

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
                Boolean cardPrivate = myApp.getCardPrivate();
                Integer cardDesign = myApp.getCardDesign();

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
                mainActivity.setFragment("home");
            }
        });

        return view;
    }
}