package com.ssafy.recto.createcard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.config.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CreateFragment_4_SelectPhoto extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private Button btn_previous;
    private Button btn_next;
    private ImageView imageView;
    private Button btn_selectphoto;
    private Bitmap bm;

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
        view = inflater.inflate(R.layout.create_fragment_4_selectphoto, container, false);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        imageView = view.findViewById(R.id.imageView);
        btn_selectphoto = view.findViewById(R.id.btn_selectphoto);
        bm = null;

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("create_selectvideo");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bm == null){
                    Toast.makeText(getActivity(), "사진을 등록해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean cardPublic = myApp.getCardPublic();
                    Integer cardDesign = myApp.getCardDesign();

                    if (cardPublic && (cardDesign == 1)) {
                        mainActivity.setFragment("create_writeinfo_puon");
                    } else if (cardPublic && (cardDesign == 2)) {
                        mainActivity.setFragment("create_writeinfo_puph");
                    } else if (!cardPublic && (cardDesign == 1)) {
                        mainActivity.setFragment("create_writeinfo_pron");
                    } else if (!cardPublic && (cardDesign == 2)) {
                        mainActivity.setFragment("create_writeinfo_prph");
                    }
                }

            }
        });

        btn_selectphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, 101);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 101 && resultCode == getActivity().RESULT_OK){
            try{
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                bm = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                Glide.with(getContext()).load(data.getData()).into(imageView);
                inputStream.close();
                myApp.setCardPhoto(String.valueOf(data.getData()));
                saveBitmapToJpeg(bm);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(requestCode == 101 && resultCode == getActivity().RESULT_CANCELED){
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getActivity().getCacheDir(), "photo");
        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (Exception e) {
        }
    }
}