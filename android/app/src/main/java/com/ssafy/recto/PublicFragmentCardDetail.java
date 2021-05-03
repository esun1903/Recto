package com.ssafy.recto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PublicFragmentCardDetail extends Fragment {

    private View view;

    ImageView cardImageView;

    int cardImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.public_fragment_card_detail, container, false);

        cardImageView = view.findViewById(R.id.card_image_detail);
//        getData();
//        setData();

        return view;
    }

//    private void getData() {
//        if(getActivity().getIntent().hasExtra("images")) {
//            cardImage = getActivity().getIntent().getIntExtra("images", 1);
//        } else {
//            Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void setData() {
//        cardImageView.setImageResource(cardImage);
//    }
}
