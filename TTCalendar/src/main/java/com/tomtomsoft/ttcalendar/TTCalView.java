package com.tomtomsoft.ttcalendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import java.util.Calendar;
import java.util.Date;

public class TTCalView extends LinearLayout {

    public final int STATE_SELECTED = 1;
    private final int CELL_COUNT = 7*6;


    private int nCurrentYear = 0;
    private int nCurrentMonth = 0;

    int attrFontFamilyId = 0;
    int attrTextColor = 0;
    Boolean attrHeader = true;
    int attrHeaderBg = 0;


    public class TTDateCell {

        int nState = 0;

        public int y;
        public int m;
        public int d;
        public int state = 0;

        public LinearLayout llCell = null;

        public TTDateCell(int y, int m, int d, int state, LinearLayout cell) {
            this.y = y;
            this.m = m;
            this.d = d;
            this.llCell = cell;
        }
        public void reset() {
            y = m = d = state = 0;
            //llCell = null;  // !!!!
        }
        public void setData(int y, int m, int d, int state) {
            this.y = y;
            this.m = m;
            this.d = d;
            this.state = state;
        }

    }
    TTDateCell mDateCell[] = new TTDateCell[CELL_COUNT];


    public interface TTCalViewX {

        void onDaySelected(int y, int m, int d, int state);
        void onPrevMonth();
        void onNextMonth();

    }
    public TTCalViewX listener = null;

    private Calendar mCalendar = Calendar.getInstance();
    private View mCalView = null;


    public TTCalView(Context context) {
        super(context);
    }

    public TTCalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttrs(context, attrs);
        mCalView = initView();
        setCalendar(mCalView);
    }

    public TTCalView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttrs(context, attrs);
        mCalView = initView();
        setCalendar(mCalView);
    }

    public TTCalView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initAttrs(context, attrs);
        mCalView = initView();
        setCalendar(mCalView);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TTCalView,
                0, 0);


        try {
            attrFontFamilyId = a.getResourceId(R.styleable.TTCalView_ttcal_textfont, 0);
            //fontFamily = a.getString(R.styleable.TTCalView_ttcal_textfont);

            attrTextColor = a.getColor(R.styleable.TTCalView_ttcal_textcolor, 0);
            attrHeader = a.getBoolean(R.styleable.TTCalView_ttcal_header, true);
            attrHeaderBg = a.getColor(R.styleable.TTCalView_ttcal_headerbg, 0);
        } finally {
            a.recycle();
        }

    }

    private String arrWeekTitle[] = {"일", "월", "화", "수", "목", "금", "토"};

    private View initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.layout_ttcalview, this, false);
        addView(v);
//        bg = (LinearLayout) findViewById(R.id.bg);
//        symbol = (ImageView) findViewById(R.id.symbol);
//        text = (TextView) findViewById(R.id.text);

        v.findViewById(R.id.ttcalview_left_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //movePrevMonth();
                if(listener!=null) {
                    listener.onPrevMonth();
                }
            }
        });
        v.findViewById(R.id.ttcalview_right_btn).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                //moveNextMonth();
                if(listener!=null) {
                    listener.onNextMonth();
                }
            }
        });

        //LinearLayout ll = v.findViewById(R.id.ttcalview_week_title);
        LinearLayout llWeekArea = getWeekArea();
        for(int i=0; i<llWeekArea.getChildCount(); i++) {
            LinearLayout llWeekChild = (LinearLayout) llWeekArea.getChildAt(i);
            TextView tvWeekChild = llWeekChild.findViewById(R.id.ttcalview_weekcell_tv);
            tvWeekChild.setText(arrWeekTitle[i]);

            int clrText = Color.parseColor("#030303");
            if(i==0) {
                clrText = Color.parseColor("#ff1212");
            } else if(i==6) {
                clrText = Color.parseColor("#1062e0");
            }
            tvWeekChild.setTextColor(clrText);

            if (attrFontFamilyId > 0) {
                tvWeekChild.setTypeface(ResourcesCompat.getFont(getContext(), attrFontFamilyId));
            }
        }

        for(int i=0; i<CELL_COUNT; i++) {
            LinearLayout llCell = getCell(v, i);
            mDateCell[i] = new TTDateCell(0,0,0,0, llCell);

            llCell.setTag(String.valueOf(i));
            llCell.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        int pos = Integer.parseInt((String)llCell.getTag());
                        TTDateCell cellDate = mDateCell[pos];
                        if(cellDate!=null) {
                            listener.onDaySelected(cellDate.y, cellDate.m, cellDate.d, cellDate.nState);
                        }

                    }
                }
            });
            TextView tvCell = llCell.findViewById(R.id.ttcalview_cell_tv);
            if (attrFontFamilyId > 0) {
                tvCell.setTypeface(ResourcesCompat.getFont(getContext(), attrFontFamilyId));
            }
        }

//        for(int i=0; i<CELL_COUNT; i++) {
//            LinearLayout llCell = getCell(v, i);
//            llCell.setTag(String.valueOf(i));
//            llCell.setOnClickListener(new OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listener!=null) {
//                        int pos = Integer.parseInt((String)llCell.getTag());
//                        GTKDateCell cellDate = mDateCell[pos];
//                        if(cellDate!=null) {
//                            listener.onDaySelected(cellDate.y, cellDate.m, cellDate.d, cellDate.nState);
//                        }
//
//                    }
//                }
//            });
//        }

        /* HEADER */
        LinearLayout llHeaderArea = getHeaderArea();
        showHeader(attrHeader);
        if(attrHeaderBg !=0 ) {
            llHeaderArea.setBackgroundColor(attrHeaderBg);
        }
        TextView tvTitle = v.findViewById(R.id.ttcalview_title_tv);
        if (attrFontFamilyId > 0) {
            tvTitle.setTypeface(ResourcesCompat.getFont(getContext(), attrFontFamilyId));
        }

        nCurrentYear = mCalendar.get(Calendar.YEAR);
        nCurrentMonth = mCalendar.get(Calendar.MONTH) + 1;

        return v;
    }

    private void setCalendar(View v) {

        for(int i=0; i<CELL_COUNT; i++) {
            mDateCell[i].reset();
        }

//        int nYear = mCalendar.get(Calendar.YEAR);
//        int nMonth = mCalendar.get(Calendar.MONTH) + 1;

        mCalendar.set(nCurrentYear, nCurrentMonth-1, 1, 1, 1, 1);
        int nDayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);

        int days = mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);


        TextView tvTitle = v.findViewById(R.id.ttcalview_title_tv);
        String sTitle = String.format("%d년 %d월", nCurrentYear, nCurrentMonth);
        tvTitle.setText(sTitle);

        Calendar cal = Calendar.getInstance();
        cal.setTime(mCalendar.getTime());

        int k = 0;
        int nPrevDays = nDayOfWeek-1;
        for(int i=0; i<nPrevDays; i++, k++) {
            //LinearLayout llCell = getCell(v, i);
            LinearLayout llCell = mDateCell[k].llCell;
            TextView tvCell = llCell.findViewById(R.id.ttcalview_cell_tv);
            tvCell.setText("");
            tvCell.setBackgroundResource(0);

            //mDateCell[k] = new GTKDateCell(0,0,0, llCell);
            mDateCell[k].setData(0,0,0,0);
        }

        for(int i=0; i<days; i++, k++) {
            //LinearLayout llCell = getCell(v, nPrevDays+i);
            LinearLayout llCell = mDateCell[k].llCell;
            TextView tvCell = llCell.findViewById(R.id.ttcalview_cell_tv);
            tvCell.setText(String.valueOf(i+1));
            tvCell.setBackgroundResource(0);

            mDateCell[k].setData(nCurrentYear, nCurrentMonth, i+1,0);
        }

        for(int i=0; i<(CELL_COUNT-(nPrevDays+days)); i++) {
            //LinearLayout llCell = getCell(v, nPrevDays+days+i);
            LinearLayout llCell = mDateCell[k].llCell;
            TextView tvCell = llCell.findViewById(R.id.ttcalview_cell_tv);
            tvCell.setText("");
            tvCell.setBackgroundResource(0);

            mDateCell[k].setData(0,0,0,0);
        }
        for(; k<CELL_COUNT; k++) {
            LinearLayout llCell = mDateCell[k].llCell;
            TextView tvCell = llCell.findViewById(R.id.ttcalview_cell_tv);
            tvCell.setText("");
            tvCell.setBackgroundResource(0);

            mDateCell[k].setData(0,0,0,0);
        }

    }
    public void movePrevMonth() {

        mCalendar.add(Calendar.MONTH, -1);
        setCalendar(mCalView);
    }
    public void moveNextMonth() {

        mCalendar.add(Calendar.MONTH, 1);
        setCalendar(mCalView);

        //selectDate(2021, 3, 20, true);
    }

    int weeks[] = {R.id.ttcalview_week1, R.id.ttcalview_week2, R.id.ttcalview_week3, R.id.ttcalview_week4, R.id.ttcalview_week5, R.id.ttcalview_week6 };


//    private LinearLayout getCell(View v, int y, int x) {
//
//        LinearLayout ll = v.findViewById(weeks[y]);
//        LinearLayout llCell = (LinearLayout) ll.getChildAt(x);
//        return llCell;
//    }

//    public void setShowTitle(Boolean bShow) {
//        attrHeader = bShow;
//        invalidate();
//        requestLayout();
//    }

    public void selectDate(int y, int m, int d, Boolean bSelect) {

        //LinearLayout llCell = findCellByDate(y, m, d);
        TTDateCell cell = findGTKCellByDate(y, m, d);
        LinearLayout llCell = cell.llCell;

        if(llCell!=null) {
            TextView tv = llCell.findViewById(R.id.ttcalview_cell_tv);
            if(bSelect) {
                tv.setBackgroundResource(R.drawable.xml_border_circle2);
                cell.nState += STATE_SELECTED;
            } else {
                tv.setBackgroundResource(0);
                cell.nState ^= STATE_SELECTED;
            }
        }
    }


    private LinearLayout getCell(View v, int pos) {
        int y = pos/7;
        int x = pos%7;
        LinearLayout ll = v.findViewById(weeks[y]);
        LinearLayout llCell = (LinearLayout) ll.getChildAt(x);
        return llCell;
    }


    public TTDateCell findGTKCellByDate(int y, int m, int d) {
        TTDateCell cellFound = null;
        for(int i=0; i<CELL_COUNT; i++) {
            TTDateCell cell = mDateCell[i];
            if(cell!=null && cell.y==y && cell.m==m && cell.d==d) {
                cellFound = cell;
                break;
            }
        }
        return cellFound;
    }

//    public LinearLayout findCellByDate(int y, int m, int d) {
//        LinearLayout llCell = null;
//        for(int i=0; i<CELL_COUNT; i++) {
//            TTDateCell cell = mDateCell[i];
//            if(cell!=null && cell.y==y && cell.m==m && cell.d==d) {
//                llCell = cell.llCell;
//                break;
//            }
//        }
//        return llCell;
//    }

    public void showHeader(Boolean bShow) {

        //LinearLayout llHeader = findViewById(R.id.ttcalview_header);
        LinearLayout llHeaderArea = getHeaderArea();
        llHeaderArea.setVisibility(bShow?View.VISIBLE:View.GONE);
    }

    public LinearLayout getHeaderArea() {
        LinearLayout llHeaderArea = findViewById(R.id.ttcalview_header);
        return llHeaderArea;
    }
    public LinearLayout getWeekArea() {
        LinearLayout llWeekArea = findViewById(R.id.ttcalview_week_title);
        return llWeekArea;
    }
    public TTDateCell[] getDays() {
        return mDateCell;
    }

    public TextView getHeaderTitle() {
        TextView tvTitle = findViewById(R.id.ttcalview_title_tv);
        return tvTitle;
    }
    public void setDate(int y, int m) {

        this.nCurrentYear = y;
        this.nCurrentMonth = m;

        setCalendar(mCalView);
    }
}
