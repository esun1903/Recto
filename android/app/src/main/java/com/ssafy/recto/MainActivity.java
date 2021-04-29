package com.ssafy.recto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private HomeFragment homeFragment;
    private CreateFragment createFragment;
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
                        setFragment(0);
                        break;
                    case R.id.nav_create:
                        setFragment(1);
                        break;
                    case R.id.nav_scan:
                        setFragment(2);
                        break;
                    case R.id.nav_public:
                        setFragment(3);
                        break;
                    case R.id.nav_profile:
                        setFragment(4);
                        break;
                }
                return true;
            }
        });

        homeFragment = new HomeFragment();
        createFragment = new CreateFragment();
        scanFragment = new ScanFragment();
        publicFragment = new PublicFragment();
        profileFragment = new ProfileFragment();

        setFragment(0);
    }
    
    // fragment 교체
    private void setFragment(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        switch (n) {
            case 0:
                ft.replace(R.id.main_frame, homeFragment);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, createFragment);
                ft.commit();
                break;
            case 2:
                ft.replace(R.id.main_frame, scanFragment);
                ft.commit();
                break;
            case 3:
                ft.replace(R.id.main_frame, publicFragment);
                ft.commit();
                break;
            case 4:
                ft.replace(R.id.main_frame, profileFragment);
                ft.commit();
                break;
        }
    }
}