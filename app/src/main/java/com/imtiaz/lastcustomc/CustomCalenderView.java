package com.imtiaz.lastcustomc;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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

   MyGridAdapter myGridAdapter;
   AlertDialog alertDialog;
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

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                View addView  = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_newevent_layout,null);
                EditText eventName = addView.findViewById(R.id.events_id_edt);
                TextView eventTime = addView.findViewById(R.id.eventTime);
                ImageButton setTime = addView.findViewById(R.id.set_event_time);
                Button addEvent = addView.findViewById(R.id.addEvent);

                setTime.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        int hours = calendar.get(Calendar.HOUR_OF_DAY);
                        int minutes = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog = new TimePickerDialog(addView.getContext(), androidx.appcompat.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                Calendar c = Calendar.getInstance();
                                c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                                c.set(Calendar.MINUTE,minute);
                                c.setTimeZone(TimeZone.getDefault());
                                SimpleDateFormat hFormate = new SimpleDateFormat("K:mm a",Locale.ENGLISH);
                                String event_Time = hFormate.format(c.getTime());
                                eventTime.setText(event_Time);

                            }
                        },hours,minutes,false);
                        timePickerDialog.show();
                    }
                });
                final String date = dateFormat.format(dates.get(position));
                final String month = monthFormat.format(dates.get(position));
                final String year = yearFormat.format(dates.get(position));


                addEvent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveEvent(eventName.getText().toString(),eventTime.getText().toString(),date,month,year);
                        setUpCalender();
                        alertDialog.dismiss();
                    }
                });

                builder.setView(addView);
                alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void saveEvent(String eventName, String eventTime, String date, String month, String year) {

        Toast.makeText(context, "Event Saved", Toast.LENGTH_SHORT).show();
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

        // Set up the calendar with current month's dates
        setUpCalender();

    }

    private void setUpCalender(){
        String currentDate = dateFormat.format(calendar.getTime());
        currentDateTxt.setText(currentDate);
        dates.clear();
        Calendar monthCalender = (Calendar) calendar.clone();
        monthCalender.set(Calendar.DAY_OF_MONTH,1);
        int firstDayOfMonth = monthCalender.get(Calendar.DAY_OF_WEEK)-1;
        monthCalender.add(Calendar.DAY_OF_MONTH, -firstDayOfMonth);

        while (dates.size() < MAX_CALENDER_DAYS){
            dates.add(monthCalender.getTime());
            monthCalender.add(Calendar.DAY_OF_MONTH,1);
        }

        myGridAdapter = new MyGridAdapter(context,dates,calendar,eventsList);
        gridView.setAdapter(myGridAdapter);
    }
}
