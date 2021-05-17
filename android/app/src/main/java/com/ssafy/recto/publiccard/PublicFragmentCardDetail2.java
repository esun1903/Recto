package com.ssafy.recto.publiccard;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicFragmentCardDetail2 extends Fragment {

    ApiInterface api;
    MainActivity mainActivity;
    ImageView cardImageView;
    ImageView info_dialog;
    Button free_photo_card_list_btn;
    TextView tv_phrases;
    TextView card_id;
    Button download_button;
    private View view;
    private Context mContext;
    int seq;
    String photo_url;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.public_fragment_card_detail2, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // 카드 목록에서 photo_seq 값 (sep[pos]) 가져오기
        Bundle bundle = getArguments();
        if (bundle != null) {
            seq = bundle.getInt("seq");
        }
        Log.d("seq 값을 가져올까", String.valueOf(seq));

        try {
            requestGet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tv_phrases = view.findViewById(R.id.tv_phrases);
        card_id = view.findViewById(R.id.card_id);
        cardImageView = view.findViewById(R.id.card_image_detail);
        free_photo_card_list_btn = view.findViewById(R.id.free_photo_card_list_btn);
        info_dialog = view.findViewById(R.id.info_dialog);
        download_button = view.findViewById(R.id.download_button);

        // 목록보기 버튼
        free_photo_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("public");
            }
        });

        // 다이얼로그
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

        // 버튼 눌렀을 때 카드 갤러리에 저장하기
        download_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 권한 허용
                checkSelfPermission();

                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RECTO";
                final FrameLayout capture = view.findViewById(R.id.card_frameLayout);

                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                    Toast.makeText(getContext(), "폴더가 생성되었습니다.", Toast.LENGTH_SHORT).show();
                }

                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddmmss");
                Date date = new Date();
                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path + "/RECTO" + day.format(date) + ".jpeg");
                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    mainActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path + "/RECTO" + day.format(date) + ".JPEG")));
//                    mainActivity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED,Uri.parse("file://"+Environment.getExternalStorageDirectory()+"/RECTO")));
                    Toast.makeText(getContext(), "저장완료", Toast.LENGTH_SHORT).show();
                    fos.flush();
                    fos.close();
                    capture.destroyDrawingCache();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

    public void requestGet() throws ParseException {
        // photo_seq 값으로 포토카드 검색
        Call<CardData> call = api.getCard(seq);
        call.enqueue(new Callback<CardData>() {
            @Override
            public void onResponse(Call<CardData> call, Response<CardData> response) {
                Log.d("카드 정보 가져오기", String.valueOf(response.body()));
                String uid, id, video, photo, phrase, date, pwd, url;
                int photo_seq, design;

                id = response.body().getPhoto_id();
                phrase = response.body().getPhrase();

                photo_url = response.body().getPhoto_url();

                // 문구 넣어주기
                tv_phrases.setText(phrase);

                // 아이디 넣어주기
                card_id.setText(id);

                // 이미지 불러오기
                Glide.with(getContext()).load(photo_url).into(cardImageView);
            }

            @Override
            public void onFailure(Call<CardData> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
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

}
