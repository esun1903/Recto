package com.ssafy.recto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.ssafy.recto.arcore.ArMainActivity;
import com.ssafy.recto.config.MyApplication;
import com.ssafy.recto.createcard.CreateFragment_1_SelectOpen;
import com.ssafy.recto.createcard.CreateFragment_2_SelectDesign;
import com.ssafy.recto.createcard.CreateFragment_3_SelectVideo;
import com.ssafy.recto.createcard.CreateFragment_4_SelectPhoto;
import com.ssafy.recto.createcard.CreateFragment_5_WriteInfo_pron;
import com.ssafy.recto.createcard.CreateFragment_5_WriteInfo_prph;
import com.ssafy.recto.createcard.CreateFragment_5_WriteInfo_puon;
import com.ssafy.recto.createcard.CreateFragment_5_WriteInfo_puph;
import com.ssafy.recto.createcard.CreateFragment_6_Success_onlyphoto;
import com.ssafy.recto.createcard.CreateFragment_6_Success_phrases;
import com.ssafy.recto.home.HomeFragment;
import com.ssafy.recto.home.InfoFragment;
import com.ssafy.recto.mypage.ProfileFragment;
import com.ssafy.recto.mypage.ProfileFragmentGift;
import com.ssafy.recto.mypage.ProfileFragmentGiftDetail;
import com.ssafy.recto.mypage.ProfileFragmentGiftDetail2;
import com.ssafy.recto.mypage.ProfileFragmentMine;
import com.ssafy.recto.mypage.ProfileFragmentMineDetail;
import com.ssafy.recto.mypage.ProfileFragmentMineDetail2;
import com.ssafy.recto.publiccard.PublicFragment;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail;
import com.ssafy.recto.publiccard.PublicFragmentCardDetail2;
import com.ssafy.recto.user.LoginActivity;
import com.ssafy.recto.user.UserAccount;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageView iv_info;
    private ImageView iv_recto;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private CreateFragment_1_SelectOpen createFragment1SelectOpen;
    private CreateFragment_2_SelectDesign createFragment2SelectDesign;
    private CreateFragment_3_SelectVideo createFragment3SelectVideo;
    private CreateFragment_4_SelectPhoto createFragment4SelectPhoto;
    private CreateFragment_5_WriteInfo_prph createFragment5WriteInfoPrph;
    private CreateFragment_5_WriteInfo_pron createFragment5WriteInfoPron;
    private CreateFragment_5_WriteInfo_puph createFragment5WriteInfoPuph;
    private CreateFragment_5_WriteInfo_puon createFragment5WriteInfoPuon;
    private CreateFragment_6_Success_onlyphoto createFragment6SuccessOnlyPhoto;
    private CreateFragment_6_Success_phrases createFragment6SuccessPhrases;
    private PublicFragment publicFragment;
    private PublicFragmentCardDetail publicFragmentCardDetail;
    private PublicFragmentCardDetail2 publicFragmentCardDetail2;
    private ProfileFragment profileFragment;
    private ProfileFragmentMine profileFragmentMine;
    private ProfileFragmentMineDetail profileFragmentMineDetail;
    private ProfileFragmentMineDetail2 profileFragmentMineDetail2;
    private ProfileFragmentGift profileFragmentGift;
    private ProfileFragmentGiftDetail profileFragmentGiftDetail;
    private ProfileFragmentGiftDetail2 profileFragmentGiftDetail2;
    private InfoFragment infoFragment;
    private FirebaseAuth mFirebaseAuth;
    MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        iv_info = findViewById(R.id.iv_info);
        iv_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frame);
                if(fragment instanceof InfoFragment) {
                    onBackPressed();
                }
                else {
                    setFragment("information");
                    ft.addToBackStack(null);
                }
            }
        });
        iv_recto = findViewById(R.id.iv_recto);
        iv_recto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment("home");
            }
        });

        mFirebaseAuth = FirebaseAuth.getInstance();

//        myApplication = new MyApplication();
////        myApplication.setGoogleNickname(null);
//        try {
//            myApplication.getGoogleNickname();
//        } catch (Exception e) {
//            myApplication.setGoogleNickname("null");
//        }

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        setFragment("home");
                        ft.addToBackStack(null);
                        break;
                    case R.id.nav_create:
                        try {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            String UserUid = account.setIdToken(firebaseUser.getUid());
                            setFragment("create_selectopen");
                            ft.addToBackStack(null);
                        } catch (Exception e) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case R.id.nav_scan:
                        Intent intentScan = new Intent(MainActivity.this, ArMainActivity.class);
                        startActivity(intentScan);
                        break;
                    case R.id.nav_public:
                        setFragment("public");
                        ft.addToBackStack(null);
                        break;
                    case R.id.nav_profile:
                        try {
                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            String UserUid = account.setIdToken(firebaseUser.getUid());
                            setFragment("profile");
                            ft.addToBackStack(null);
                        } catch (Exception e) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        break;
                }
                return true;
            }
        });

        homeFragment = new HomeFragment();
        createFragment1SelectOpen = new CreateFragment_1_SelectOpen();
        createFragment2SelectDesign = new CreateFragment_2_SelectDesign();
        createFragment3SelectVideo = new CreateFragment_3_SelectVideo();
        createFragment4SelectPhoto = new CreateFragment_4_SelectPhoto();
        createFragment5WriteInfoPrph = new CreateFragment_5_WriteInfo_prph();
        createFragment5WriteInfoPron = new CreateFragment_5_WriteInfo_pron();
        createFragment5WriteInfoPuph = new CreateFragment_5_WriteInfo_puph();
        createFragment5WriteInfoPuon = new CreateFragment_5_WriteInfo_puon();
        createFragment6SuccessOnlyPhoto = new CreateFragment_6_Success_onlyphoto();
        createFragment6SuccessPhrases = new CreateFragment_6_Success_phrases();
        publicFragment = new PublicFragment();
        publicFragmentCardDetail = new PublicFragmentCardDetail();
        publicFragmentCardDetail2 = new PublicFragmentCardDetail2();
        profileFragment = new ProfileFragment();
        profileFragmentMine = new ProfileFragmentMine();
        profileFragmentMineDetail = new ProfileFragmentMineDetail();
        profileFragmentMineDetail2 = new ProfileFragmentMineDetail2();
        profileFragmentGift = new ProfileFragmentGift();
        profileFragmentGiftDetail = new ProfileFragmentGiftDetail();
        profileFragmentGiftDetail2 = new ProfileFragmentGiftDetail2();
        infoFragment = new InfoFragment();

        setFragment("home");
    }

    // fragment 교체
    public void setFragment(String str) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (str) {
            case "home":
                ft.replace(R.id.main_frame, homeFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_selectopen":
                ft.replace(R.id.main_frame, createFragment1SelectOpen);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_selectdesign":
                ft.replace(R.id.main_frame, createFragment2SelectDesign);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_selectvideo":
                ft.replace(R.id.main_frame, createFragment3SelectVideo);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_selectphoto":
                ft.replace(R.id.main_frame, createFragment4SelectPhoto);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_writeinfo_prph":
                ft.replace(R.id.main_frame, createFragment5WriteInfoPrph);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_writeinfo_pron":
                ft.replace(R.id.main_frame, createFragment5WriteInfoPron);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_writeinfo_puph":
                ft.replace(R.id.main_frame, createFragment5WriteInfoPuph);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_writeinfo_puon":
                ft.replace(R.id.main_frame, createFragment5WriteInfoPuon);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_success_onlyphoto":
                ft.replace(R.id.main_frame, createFragment6SuccessOnlyPhoto);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "create_success_phrases":
                ft.replace(R.id.main_frame, createFragment6SuccessPhrases);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "public":
                ft.replace(R.id.main_frame, publicFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "public_card_detail":
                ft.replace(R.id.main_frame, publicFragmentCardDetail);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "public_card_detail2":
                ft.replace(R.id.main_frame, publicFragmentCardDetail2);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile":
                ft.replace(R.id.main_frame, profileFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_mine":
                ft.replace(R.id.main_frame, profileFragmentMine);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_mine_detail":
                ft.replace(R.id.main_frame, profileFragmentMineDetail);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_mine_detail2":
                ft.replace(R.id.main_frame, profileFragmentMineDetail2);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_gift":
                ft.replace(R.id.main_frame, profileFragmentGift);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_gift_detail":
                ft.replace(R.id.main_frame, profileFragmentGiftDetail);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "profile_gift_detail2":
                ft.replace(R.id.main_frame, profileFragmentGiftDetail2);
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "information":
                ft.replace(R.id.main_frame, infoFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
    }
}