package com.ssafy.recto;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private CreateFragment_1_SelectOpen createFragment1SelectOpen;
    private CreateFragment_2_SelectDesign createFragment2SelectDesign;
    private ScanFragment scanFragment;
    private PublicFragment publicFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        setFragment("home");
                        break;
                    case R.id.nav_create:
                        setFragment("create");
                        break;
                    case R.id.nav_scan:
                        setFragment("scan");
                        break;
                    case R.id.nav_public:
                        setFragment("public");
                        break;
                    case R.id.nav_profile:
                        setFragment("profile");
                        break;
                }
                return true;
            }
        });

        homeFragment = new HomeFragment();
        createFragment1SelectOpen = new CreateFragment_1_SelectOpen();
        createFragment2SelectDesign = new CreateFragment_2_SelectDesign();
        scanFragment = new ScanFragment();
        publicFragment = new PublicFragment();
        profileFragment = new ProfileFragment();

        setFragment("home");
    }
    
    // fragment 교체
    protected void setFragment(String str) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (str) {
            case "home":
                ft.replace(R.id.main_frame, homeFragment);
                ft.commit();
                break;
            case "create":
                ft.replace(R.id.main_frame, createFragment1SelectOpen);
                ft.commit();
                break;
            case "scan":
                ft.replace(R.id.main_frame, scanFragment);
                ft.commit();
                break;
            case "public":
                ft.replace(R.id.main_frame, publicFragment);
                ft.commit();
                break;
            case "profile":
                ft.replace(R.id.main_frame, profileFragment);
                ft.commit();
                break;
            case "":
                ft.replace(R.id.main_frame, createFragment2SelectDesign);
                ft.commit();
                break;
        }
    }
}