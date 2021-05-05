package com.ssafy.recto;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

public class CreateFragment_5_WriteInfo_puph extends Fragment {

    MainActivity mainActivity;
    MyApplication myApp;
    private View view;
    private Button btn_previous;
    private Button btn_next;
    private EditText et_text;
    private static TextView tv_date;
    public static int year, month, day;

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myApp = (MyApplication) getActivity().getApplication();
        view = inflater.inflate(R.layout.create_fragment_5_writeinfo_puph, container, false);
        btn_previous = view.findViewById(R.id.btn_previous);
        btn_next = view.findViewById(R.id.btn_next);
        et_text = view.findViewById(R.id.et_text);
        tv_date = view.findViewById(R.id.tv_date);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.setFragment("create_selectphoto");
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (String.valueOf(et_text.getText()).equals("") || String.valueOf(tv_date.getText()).equals("")) {
                    Toast.makeText(getActivity(), "빠진 항목이 없는지 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show();
                }
                else {
                    myApp.setCardPhrases(String.valueOf(et_text.getText()));
                    myApp.setCardDate(String.valueOf(tv_date.getText()));
                    mainActivity.setFragment("create_success");
                }
            }
        });

        tv_date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DialogFragment datePickerFragment = new DatePickerHoloLight();
                datePickerFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        return view;
    }

    public static class DatePickerHoloLight extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DatePickerDialog theme_holo_light = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, year, month, day);
            return theme_holo_light;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String date = year + "년 " + (month+1) + "월 " + day + "일";
            tv_date.setText(date);
        }
    }
}