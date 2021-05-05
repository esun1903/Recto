package com.ssafy.recto;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CreateFragment_6_Success extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private Button btn_previous;
    private Button btn_next;

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
        view = inflater.inflate(R.layout.create_fragment_6_success, container, false);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);

//        Log.e("d", myApp.getCardDate());
//        Log.e("d", myApp.getCardPassword());
//        Log.e("d", myApp.getCardPhrases());

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean cardPrivate = myApp.getCardPrivate();
                Boolean cardOnlyPhoto = myApp.getCardOnlyPhoto();
                if (cardPrivate && cardOnlyPhoto) {
                    mainActivity.setFragment("create_writeinfo_pron");
                }
                else if (cardPrivate && !cardOnlyPhoto) {
                    mainActivity.setFragment("create_writeinfo_prph");
                }
                else if (!cardPrivate && cardOnlyPhoto) {
                    mainActivity.setFragment("create_writeinfo_puon");
                }
                else if (!cardPrivate && !cardOnlyPhoto) {
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