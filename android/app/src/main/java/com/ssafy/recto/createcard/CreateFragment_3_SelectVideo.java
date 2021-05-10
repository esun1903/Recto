package com.ssafy.recto.createcard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.R;

public class CreateFragment_3_SelectVideo extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private Button btn_previous;
    private Button btn_next;
    private VideoView videoView;
    private Button btn_selectvideo;
    private Uri fileUri;

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
        view = inflater.inflate(R.layout.create_fragment_3_selectvideo, container, false);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        videoView = view.findViewById(R.id.videoView);
        btn_selectvideo = view.findViewById(R.id.btn_selectvideo);
        fileUri = null;

        checkSelfPermission();

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("create_selectdesign");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileUri == null) {
                    Toast.makeText(getActivity(), "동영상을 업로드해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    mainActivity.setFragment("create_selectphoto");
                }
            }
        });

        btn_selectvideo.setOnClickListener(new View.OnClickListener() {
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
                MediaController mc = new MediaController(getActivity());
                videoView.setMediaController(mc);
                fileUri = data.getData();
                videoView.setVideoPath(String.valueOf(fileUri));
                videoView.start();
                myApp.setCardVideo(String.valueOf(fileUri));
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(requestCode == 101 && resultCode == getActivity().RESULT_CANCELED){
            Toast.makeText(getActivity(),"취소", Toast.LENGTH_SHORT).show();
        }
    }
}
