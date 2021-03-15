package com.tomtomsoft.ttcalendardemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tomtomsoft.ttcalendar.TTCalView;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    TTCalView calView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calView = findViewById(R.id.calView);
        calView.showHeader(true);
        calView.selectDate(2021, 3, 14, true);
        calView.listener = new TTCalView.TTCalViewX() {
            @Override
            public void onDaySelected(int y, int m, int d, int state) {

                String sTemp = String.format("day selected: y=%d,m=%d,d=%d, state=%d",
                        y, m, d, state);
                Log.d(TAG, sTemp);
            }

            @Override
            public void onPrevMonth() {

                calView.movePrevMonth();
            }

            @Override
            public void onNextMonth() {
                calView.moveNextMonth();
            }
        };

        // date loop
        TTCalView.TTDateCell cells[] = calView.getDays();
        for(int i=0; i<cells.length; i++) {

            String sTemp = String.format("cell[%d]: y=%d,m=%d,d=%d",
                    i, cells[i].y, cells[i].m, cells[i].m);
            Log.d(TAG, sTemp);
        }

        // Get Header area
        LinearLayout llHeaderArea = calView.getHeaderArea();

        // Get Week title rea.
        LinearLayout llWeekTitleArea = calView.getWeekArea();

        // Get Title TextView
        TextView tvTitle = calView.getHeaderTitle();


        calView.setDate(2021, 9);



    }
}