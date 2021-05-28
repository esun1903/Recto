package com.ssafy.recto.createcard;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.config.MyApplication;

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
                    Toast.makeText(getActivity(), "영상을 등록해주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    mainActivity.setFragment("create_selectphoto");
                }
            }
        });

        btn_selectvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(android.provider.MediaStore.Video.Media.CONTENT_TYPE);
                startActivityForResult(intent, 101);
            }
        });

        return view;
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
        }
    }
}
