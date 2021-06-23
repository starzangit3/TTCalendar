package com.tomtomsoft.ttcalendardemo;

import android.os.Bundle;
import android.util.Log;
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

    private String TAG = "CalendarPopup";
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

        calView.listener = new TTCalView.TTCalViewX() {
            @Override
            public void onDaySelected(int y, int m, int d, int state) {

                String sTemp = String.format("click: y=%d,m=%d,d=%d", y, m, d);
                Log.d(TAG, sTemp);

                TTCalView.TTDateCell cell = null;
                cell = calView.getSelectedDate();
                calView.unselectAll();

                cell = calView.getSelectedDate();
                calView.selectDate(y, m, d, true);
                cell = calView.getSelectedDate();

            }

            @Override
            public void onPrevMonth() {

                calView.movePrevMonth();
                int y = calView.getYear();
                int m = calView.getMonth();
                int d = calView.getDay();
                String sTemp = String.format("Prev: y=%d,m=%d,d=%d", y, m, d);
                Log.d(TAG, sTemp);

            }

            @Override
            public void onNextMonth() {
                calView.moveNextMonth();
                int y = calView.getYear();
                int m = calView.getMonth();
                int d = calView.getDay();
                String sTemp = String.format("Next: y=%d,m=%d,d=%d", y, m, d);
                Log.d(TAG, sTemp);

            }
        };

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
