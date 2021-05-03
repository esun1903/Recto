package com.ssafy.recto;

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

public class PublicFragment extends Fragment{

    private View view;
    RecyclerView recyclerView;
    private GridLayoutManager mGridLayoutManager;

    MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }

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

        recyclerView = view.findViewById(R.id.recycleView);

        PublicFragmentMyAdapter publicFragmentMyAdapter = new PublicFragmentMyAdapter(getActivity(), images);
        recyclerView.setAdapter(publicFragmentMyAdapter);

        int numberOfColumns = 3;
        mGridLayoutManager = new GridLayoutManager(getActivity(), numberOfColumns);
        recyclerView.setLayoutManager(mGridLayoutManager);

        PublicFragmentMyAdapter.setOnItemClickListener(new PublicFragmentMyAdapter.OnItemClickListener()
        {
            @Override
            public void onItemClick(View v, int pos)
            {
                Log.i("this is", String.valueOf(pos));
                mainActivity.setFragment("public_card_detail");
            }
        });
        return view;
    }
}
