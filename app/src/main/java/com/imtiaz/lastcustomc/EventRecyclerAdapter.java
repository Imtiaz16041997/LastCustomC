package com.imtiaz.lastcustomc;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.imtiaz.lastcustomc.db.DBOpenHelper;

import java.util.ArrayList;

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.EventRecyclerViewHolder> {

    Context context;
    ArrayList<Events> arrayList;

    DBOpenHelper dbOpenHelper;
    public EventRecyclerAdapter(Context context, ArrayList<Events> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @NonNull
    @Override
    public EventRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_events_row_layout,parent,false);
        return new EventRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewHolder holder, int position) {
        Events events = arrayList.get(position);
        holder.eventTxt.setText(events.getEVENT());
        holder.dateTxt.setText(events.getDATE());
        holder.timeTxt.setText(events.getTIME());

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCalenderEvent(events.getEVENT(),events.getDATE(),events.getTIME());
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class EventRecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView dateTxt,eventTxt,timeTxt;
        Button deleteBtn;
        public EventRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            dateTxt = itemView.findViewById(R.id.eventDate);
            eventTxt = itemView.findViewById(R.id.eventName);
            timeTxt = itemView.findViewById(R.id.eventTime);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }

    private void deleteCalenderEvent(String event,String date, String time){
        dbOpenHelper = new DBOpenHelper(context);
        SQLiteDatabase database = dbOpenHelper.getWritableDatabase();
        dbOpenHelper.deleteEvent(event,date,time,database);
        dbOpenHelper.close();
    }
}
