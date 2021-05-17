package com.ssafy.recto.mypage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;
import com.ssafy.recto.api.ApiInterface;
import com.ssafy.recto.api.GiftData;
import com.ssafy.recto.api.HttpClient;

import java.text.ParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragmentGiftDetail extends Fragment {

    ApiInterface api;
    TextView from_id;
    TextView card_id;
    MainActivity mainActivity;
    ImageView giftImageView;
    ImageView info_dialog;
    Button gift_photo_card_list_btn;
    Button delete_button;
    private View view;
    private Context mContext;
    private FirebaseAuth mFirebaseAuth;        // Firebase 인증 처리
    private DatabaseReference mDatabaseRef;   // 실시간 데이터베이스
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_gift_card_detail, container, false);
        api = HttpClient.getRetrofit().create( ApiInterface.class );

        // 목록에서 gift_seq 값 (sep[pos]) 가져오기
        Bundle bundle = getArguments();
        if (bundle != null) {
            seq = bundle.getInt("seq");
        }

        try {
            requestGet();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        from_id = view.findViewById(R.id.from_id);
        card_id = view.findViewById(R.id.card_id);
        giftImageView = view.findViewById(R.id.card_image_detail);
        gift_photo_card_list_btn = view.findViewById(R.id.gift_photo_card_list_btn);
        info_dialog = view.findViewById(R.id.info_dialog);
        delete_button = view.findViewById(R.id.delete_button);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("recto"); // realtime DB에서 정보 가져오기

        // 목록보기 버튼
        gift_photo_card_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("profile");
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

        // 삭제 버튼 눌렀을 때 다이얼로그
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(getContext());
                ad.setTitle("Delete card");
                ad.setMessage("선물 받은 카드를 삭제하시겠습니까?");

                // 삭제 버튼
                ad.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 카드 삭제 api
                        try {
                            Call<String> call = api.deleteGiftCard(seq);
                            call.enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    Log.e("success", "yeeeeee :>" + response);
                                    Toast.makeText(getContext(), "삭제 완료", Toast.LENGTH_SHORT).show();
                                    mainActivity.setFragment("profile");
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Log.e("nooooo", "failed :<" + t);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        // 다이얼로그 닫기
                        dialog.dismiss();
                    }
                });

                // 취소 버튼
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 다이얼로그 닫기
                        dialog.dismiss();
                    }
                });

                AlertDialog alertAd = ad.create();
                alertAd.show();

                Button yes_btn = alertAd.getButton(DialogInterface.BUTTON_POSITIVE);
                yes_btn.setTextColor(Color.RED);

                Button no_btn = alertAd.getButton(DialogInterface.BUTTON_NEGATIVE);
                no_btn.setTextColor(Color.GRAY);
            }
        });

        return view;
    }

    public void requestGet() throws ParseException {
        // photo_seq 값으로 포토카드 검색
        Call<GiftData> call = api.getGift(seq);
        call.enqueue(new Callback<GiftData>() {
            @Override
            public void onResponse(Call<GiftData> call, Response<GiftData> response) {
                String from, to, photo_id, photo_url, video_url, phrase, photo_pwd;
                int photo, design, gift_seq;

                from = response.body().getGift_from();
                photo_url = response.body().getPhoto_url();
                photo_id = response.body().getPhoto_id();

                DatabaseReference UserNickname = mDatabaseRef.child("UserAccount").child(from).child("nickname");
                UserNickname.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String from_nickname = snapshot.getValue(String.class);
                        Log.e("보낸 사람 닉네임 확인", from_nickname);

                        // 보낸 사람 아이디 넣어주기
                        from_id.setText("from." + from_nickname);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                // 카드 아이디 넣어주기
                card_id.setText(photo_id);

                // 카드 이미지 넣어주기
                Glide.with(getContext()).load(photo_url).into(giftImageView);
            }

            @Override
            public void onFailure(Call<GiftData> call, Throwable t) {
                Log.e("nooooo", "failed :<" + t.toString());
            }
        });
    }
}
