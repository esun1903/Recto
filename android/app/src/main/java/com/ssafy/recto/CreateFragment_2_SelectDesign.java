package com.ssafy.recto;

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

public class CreateFragment_2_SelectDesign extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private ImageButton ib_onlyphoto;
    private ImageButton ib_phrases;
    private Button btn_previous;
    private Button btn_next;
    private Boolean cardOnlyPhoto;

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
        cardOnlyPhoto = null;

        ib_onlyphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOnlyPhoto = Boolean.TRUE;
                ib_onlyphoto.setImageResource(R.drawable.onlyphoto_frame_clicked);
                ib_phrases.setImageResource(R.drawable.phrases_frame);
            }
        });

        ib_phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardOnlyPhoto = Boolean.FALSE;
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
                if (cardOnlyPhoto == null) {
                    Toast.makeText(getActivity(), "옵션을 하나 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    myApp.setCardOnlyPhoto(cardOnlyPhoto);
                    mainActivity.setFragment("create_selectvideo");
                }
            }
        });

        return view;
    }
}
