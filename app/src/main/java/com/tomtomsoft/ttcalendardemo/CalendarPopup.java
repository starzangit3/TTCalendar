package com.tomtomsoft.ttcalendardemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.tomtomsoft.ttcalendar.TTCalView;

public class CalendarPopup extends DialogFragment {

    private View viewRoot = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        viewRoot = inflater.inflate(R.layout.popup_calendar, container);

        return viewRoot;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnCancel = view.findViewById(R.id.btn_calpopup_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        Button btnOK = view.findViewById(R.id.btn_calpopup_ok);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TTCalView calView = view.findViewById(R.id.calView);

        calView.selectDate(2021, 6, 23, true);


        ImageButton btnLeft = view.findViewById(R.id.btn_left_month);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calView.movePrevMonth();
            }
        });
        ImageButton btnRight = view.findViewById(R.id.btn_right_month);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calView.moveNextMonth();
            }
        });



    }
}
