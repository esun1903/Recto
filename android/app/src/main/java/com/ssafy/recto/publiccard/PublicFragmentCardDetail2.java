package com.ssafy.recto.publiccard;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
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
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.CardData;
import com.ssafy.recto.api.HttpClient;
import com.ssafy.recto.config.MediaScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PublicFragmentCardDetail2 extends Fragment {

    private static final boolean isLegacy = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q;
    ApiInterface api;
    MainActivity mainActivity;
    ImageView cardImageView;
    ImageView info_dialog;
    ImageView free_photo_card_list_btn;
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
            @SneakyThrows
            @Override
            public void onClick(View v) {

                final FrameLayout capture = view.findViewById(R.id.card_frameLayout);

                SimpleDateFormat day = new SimpleDateFormat("yyyyMMddmmss");
                Date date = new Date();
                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                FileOutputStream fos;
                if(isLegacy) {
                    try {
                        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/RECTO";
                        File file = new File(path);
                        if (!file.exists()){
                            file.mkdir();
                        }

                        fos = new FileOutputStream(path + "/RECTO" + day.format(date) + ".JPEG");
                        captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        MediaScanner.newInstance(getContext()).mediaScanning(path + "/RECTO" + day.format(date) + ".JPEG");
                        Toast.makeText(getContext(), "저장이 완료되었습니다", Toast.LENGTH_SHORT).show();
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    String path = Environment.DIRECTORY_DCIM + "/RECTO";
                    File file = new File(path);
                    if (!file.exists()){
                        file.mkdir();
                    }

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.RELATIVE_PATH, path);
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, "RECTO" + day.format(date));
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/JPEG");

                    ContentResolver contentResolver = getContext().getContentResolver();
                    Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                    Uri content = contentResolver.insert(collection, values);
                    try{
                        ParcelFileDescriptor pdf = contentResolver.openFileDescriptor(content, "w", null);

                        fos = new FileOutputStream(pdf.getFileDescriptor());
                        captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                        Toast.makeText(getContext(), "저장이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        fos.flush();
                        fos.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                capture.destroyDrawingCache();

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
            }
        });
    }
}
