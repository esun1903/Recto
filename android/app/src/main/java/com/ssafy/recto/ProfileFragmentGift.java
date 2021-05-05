package com.ssafy.recto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragmentGift extends Fragment {

    private View view;

    public static ProfileFragmentGift newInstance() {
        ProfileFragmentGift profileFragmentGift = new ProfileFragmentGift();
        return profileFragmentGift;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_gift, container, false);
        return view;
    }
}
