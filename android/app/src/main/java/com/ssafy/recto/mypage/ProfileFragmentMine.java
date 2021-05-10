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

public class ProfileFragmentMine extends Fragment {

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
    public static ProfileFragmentMine newInstance() {
        ProfileFragmentMine profileFragmentMine = new ProfileFragmentMine();
        return profileFragmentMine;
    }

    int[] images = {R.drawable.user1,R.drawable.user2, R.drawable.user3,
            R.drawable.user4, R.drawable.user5, R.drawable.user1,R.drawable.user2, R.drawable.user3,
            R.drawable.user4, R.drawable.user5 };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.profile_fragment_mine, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ProfileFragmentMineAdapter profileFragmentMineAdapter = new ProfileFragmentMineAdapter(getActivity(), images);
        recyclerView.setAdapter(profileFragmentMineAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        ProfileFragmentMineAdapter.setOnItemClickListener(new ProfileFragmentMineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos)
            {
                Log.i("this is", String.valueOf(pos));
                mainActivity.setFragment("profile_mine_detail");
            }
        });

        return view;
    }
}
