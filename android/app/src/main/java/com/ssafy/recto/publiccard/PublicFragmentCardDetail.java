package com.ssafy.recto.publiccard;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;

import java.text.ParseException;

public class PublicFragmentCardDetail extends Fragment {

    MainActivity mainActivity;
    ImageView cardImageView;
    ImageView info_dialog;
    Button free_photo_card_list_btn;
    private View view;
    private Context mContext;
    int seq;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.public_fragment_card_detail, container, false);

        cardImageView = view.findViewById(R.id.card_image_detail);
        free_photo_card_list_btn = view.findViewById(R.id.free_photo_card_list_btn);
        info_dialog = view.findViewById(R.id.info_dialog);

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

        // 카드 목록에서 photo_seq 값 (sep[pos]) 가져오기
        Bundle bundle = getArguments();
        if (bundle != null) {
            seq = bundle.getInt("seq");
        }
        Log.d("seq 값을 가져올까", String.valueOf(seq));



        return view;
    }
}
