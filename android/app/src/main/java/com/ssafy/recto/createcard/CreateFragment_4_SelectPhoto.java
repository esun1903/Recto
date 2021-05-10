package com.ssafy.recto.createcard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.R;

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

        checkSelfPermission();

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
                    Toast.makeText(getActivity(), "사진을 업로드 해주세요", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent();
                //기기 기본 갤러리
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //intent.setType("image/*");
                //구글 갤러리
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,101);
            }
        });

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // 권한 허용
        if(requestCode == 1){
            int length = permissions.length;
            for (int i = 0; i < length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                 Log.d("MainActivity","권한 허용 : " + permissions[i]);
                }
            }
        }
    }

    public void checkSelfPermission() {
        String temp = "";

        //파일 읽기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.READ_EXTERNAL_STORAGE + " ";
        }

        //파일 쓰기 권한 확인
        if (ContextCompat.checkSelfPermission(getActivity(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            temp += Manifest.permission.WRITE_EXTERNAL_STORAGE + " ";
        }

        if (TextUtils.isEmpty(temp) == false) {
            // 권한 요청
            ActivityCompat.requestPermissions(getActivity(), temp.trim().split(" "),1);
        } else {
            // 모두 허용 상태
            Toast.makeText(getActivity(), "권한을 모두 허용", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 101 && resultCode == getActivity().RESULT_OK){
            try{
                InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                bm = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                imageView.setImageBitmap(bm);
                inputStream.close();
                saveBitmapToJpeg(bm);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(requestCode == 101 && resultCode == getActivity().RESULT_CANCELED){
            Toast.makeText(getActivity(),"취소", Toast.LENGTH_SHORT).show();
        }
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {
        File tempFile = new File(getActivity().getCacheDir(), "photo");
        try {
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
            Toast.makeText(getContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }
}
