package com.ssafy.recto.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ssafy.recto.MainActivity;
import com.ssafy.recto.R;

public class CartFragment extends Fragment {

    MainActivity mainActivity;
    private View view;
    private Button order_button;
    private ImageView cart_count;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.cart_fragment, container, false);

        order_button = view.findViewById(R.id.order_button);
        cart_count = mainActivity.findViewById(R.id.cart_count);
        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cart_count.setImageResource(R.drawable.empty);
                mainActivity.setFragment("order");
            }
        });

        return view;
    }
}