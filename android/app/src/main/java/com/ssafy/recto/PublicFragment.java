package com.ssafy.recto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PublicFragment extends Fragment {

    private View view;
    RecyclerView recyclerView;
    private GridLayoutManager mGridLayoutManager;

    int images[] = {R.drawable.free1, R.drawable.free2, R.drawable.free3,
            R.drawable.free4, R.drawable.free5, R.drawable.free6,
            R.drawable.free7, R.drawable.free8, R.drawable.free9,
            R.drawable.free1, R.drawable.free2, R.drawable.free3,
            R.drawable.free4, R.drawable.free5, R.drawable.free6,
            R.drawable.free7, R.drawable.free8, R.drawable.free9};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.public_fragment, container, false);

        // 카드뷰
        recyclerView = view.findViewById(R.id.recycleView);

        PublicFragmentMyAdapter publicFragmentMyAdapter = new PublicFragmentMyAdapter(getActivity(), images);
        recyclerView.setAdapter(publicFragmentMyAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        return view;
    }
}
