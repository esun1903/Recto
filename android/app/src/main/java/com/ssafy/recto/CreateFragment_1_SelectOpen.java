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

public class CreateFragment_1_SelectOpen extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private ImageButton ib_private;
    private ImageButton ib_public;
    private Button btn_previous;
    private Button btn_next;
    private Boolean cardPublic;

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
        view = inflater.inflate(R.layout.create_fragment_1_selectopen, container, false);
        ib_private = view.findViewById(R.id.ib_private);
        ib_public = view.findViewById(R.id.ib_public);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        cardPublic = null;

        ib_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPublic = Boolean.FALSE;
                ib_private.setImageResource(R.drawable.private_card_clicked);
                ib_public.setImageResource(R.drawable.public_card);
            }
        });

        ib_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardPublic = Boolean.TRUE;
                ib_private.setImageResource(R.drawable.private_card);
                ib_public.setImageResource(R.drawable.public_card_clicked);
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "첫 페이지 입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cardPublic == null) {
                    Toast.makeText(getActivity(), "옵션을 하나 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    myApp.setCardPublic(cardPublic);
                    mainActivity.setFragment("create_selectdesign");
                }
            }
        });

        return view;
    }
}