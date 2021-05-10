package com.ssafy.recto.createcard;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.HttpClient;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.R;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateFragment_6_Success_onlyphoto extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    ApiInterface api;
    private View view;

    private Button btn_previous;
    private Button btn_next;
    private ImageView iv_photo;
    private TextView tv_date;

    private Boolean cardPublic;
    private Integer cardDesign;
    private String cardVideo;
    private String cardPhoto;
    private String cardPhrases;
    private String cardDate;
    private String cardDateNum;
    private String cardPassword;
    String filePath = "";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myApp = (MyApplication) getActivity().getApplication();
        api = HttpClient.getRetrofit().create(ApiInterface.class);
        view = inflater.inflate(R.layout.create_fragment_6_success_onlyphoto, container, false);

        cardPublic = myApp.getCardPublic();
        cardDesign = myApp.getCardDesign();
        cardVideo = myApp.getCardVideo();
        cardPhrases = myApp.getCardPhrases();
        cardDateNum = myApp.getCardDateNum();
        cardPassword = myApp.getCardPassword();

        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        tv_date = view.findViewById(R.id.tv_date);
        iv_photo = view.findViewById(R.id.iv_photo);

        tv_date.setText(cardDate);

        try {
            String imgpath = getActivity().getCacheDir() + "/photo";   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
            iv_photo.setImageBitmap(bm);
        } catch (Exception e) {
            Toast.makeText(getContext(), "사진 로드 실패", Toast.LENGTH_SHORT).show();
        }

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    requestPost();
                    Toast.makeText(getContext(), "포토카드 생성에 성공하셨습니다:>", Toast.LENGTH_SHORT).show();
                    mainActivity.setFragment("home");
                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }


    public void requestPost() throws Exception {

        Uri videoUri = Uri.parse(cardVideo);
        String str2 = getRealPathFromUri(videoUri);
        File file2 = new File(str2);
        RequestBody videoBody = RequestBody.create(MediaType.parse("multipart/form-data"), file2);
        MultipartBody.Part videoPart = MultipartBody.Part.createFormData("video", file2.getName(), videoBody);


        cardPhoto = myApp.getCardPhoto();
        Uri imageUri = Uri.parse(cardPhoto);
        String str = getRealPathFromUri(imageUri);
        File file = new File(str);

        RequestBody photoBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part photoPart = MultipartBody.Part.createFormData("photo", file.getName(), photoBody);


        Call<String> call = api.requestCreateCard("1", cardPublic, cardDesign, videoPart, photoPart, cardPhrases, cardDateNum, cardPassword);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.e("success", "yeeeeee :>" + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t);
            }
        });
    }


    //Uri -- > 절대경로로 바꿔서 리턴시켜주는 메소드
    String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getContext(), uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }
}