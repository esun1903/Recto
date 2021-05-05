package com.ssafy.recto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragmentMine extends Fragment {

    private View view;

    public static ProfileFragmentMine newInstance() {
        ProfileFragmentMine profileFragmentMine = new ProfileFragmentMine();
        return profileFragmentMine;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_mine, container, false);
        return view;
    }
}
