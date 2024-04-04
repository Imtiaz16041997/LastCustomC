package com.imtiaz.lastcustomc;

import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomCalenderView extends LinearLayout {

    ImageButton nextBtn, prevBtn;
    TextView currentDateTxt;
    GridView gridView;
    private static final int MAX_CALENDER_DAYS = 42;
   Calendar calendar = Calendar.getInstance(Locale.ENGLISH);
   Context context;
   SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",Locale.ENGLISH);
   SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM",Locale.ENGLISH);
   SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",Locale.ENGLISH);

   List<Date> dates = new ArrayList<>();
   List<Events> eventsList = new ArrayList<>();



    public CustomCalenderView(Context context){
        super(context);
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attributeSet){
        super(context,attributeSet);
        this.context = context;
        InitializeLayout();

        prevBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,-1);
                setUpCalender();
            }
        });

        nextBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar.add(Calendar.MONTH,1);
                setUpCalender();
            }
        });
    }

    public CustomCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void InitializeLayout(){
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.calendar_layout,this);
        nextBtn = view.findViewById(R.id.nextBtn);
        prevBtn = view.findViewById(R.id.previousBtn);
        currentDateTxt = view.findViewById(R.id.currentDate);
        gridView = view.findViewById(R.id.gridView);

        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTxt.setText(currentDate);
    }

    private void setUpCalender(){
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTxt.setText(currentDate);
    }
}
