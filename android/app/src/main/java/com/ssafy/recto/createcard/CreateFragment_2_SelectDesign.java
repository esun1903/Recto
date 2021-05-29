package com.ssafy.recto.createcard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.R;

public class CreateFragment_2_SelectDesign extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private ImageButton ib_onlyphoto;
    private ImageButton ib_phrases;
    private Button btn_previous;
    private Button btn_next;
    private Integer cardDesign;

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
        view = inflater.inflate(R.layout.create_fragment_2_selectdesign, container, false);
        ib_onlyphoto = view.findViewById(R.id.ib_onlyphoto);
        ib_phrases = view.findViewById(R.id.ib_phrases);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        cardDesign = 0;

        ib_onlyphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDesign = 1;
                ib_onlyphoto.setImageResource(R.drawable.onlyphoto_frame_clicked);
                ib_phrases.setImageResource(R.drawable.phrases_frame);
            }
        });

        ib_phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardDesign = 2;
                ib_onlyphoto.setImageResource(R.drawable.onlyphoto_frame);
                ib_phrases.setImageResource(R.drawable.phrases_frame_clicked);
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("create_selectopen");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardDesign == 0) {
                    Toast.makeText(getActivity(), "옵션을 하나 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    myApp.setCardDesign(cardDesign);
                    mainActivity.setFragment("create_selectvideo");
                }
            }
        });

        return view;
    }
}
