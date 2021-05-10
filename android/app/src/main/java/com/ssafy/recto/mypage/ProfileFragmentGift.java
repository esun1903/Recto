package com.ssafy.recto.mypage;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;

public class ProfileFragmentGift extends Fragment {

    RecyclerView recyclerView;
    MainActivity mainActivity;
    private View view;
    private GridLayoutManager mGridLayoutManager;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

    // 뷰페이저를 위한 인스탠스
    public static ProfileFragmentGift newInstance() {
        ProfileFragmentGift profileFragmentGift = new ProfileFragmentGift();
        return profileFragmentGift;
    }

    int[] images = {R.drawable.gift1, R.drawable.gift2, R.drawable.gift3,
            R.drawable.gift4, R.drawable.gift5 };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_gift, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ProfileFragmentGiftAdapter profileFragmentGiftAdapter = new ProfileFragmentGiftAdapter(getActivity(), images);
        recyclerView.setAdapter(profileFragmentGiftAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        ProfileFragmentGiftAdapter.setOnItemClickListener(new ProfileFragmentGiftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos)
            {
                Log.i("this is", String.valueOf(pos));
                mainActivity.setFragment("profile_gift_detail");
            }
        });

        return view;
    }
}
