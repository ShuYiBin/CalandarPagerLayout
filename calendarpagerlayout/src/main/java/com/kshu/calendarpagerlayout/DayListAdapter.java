package com.kshu.calendarpagerlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Date;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {
    private ArrayList<String> days;
    private RecyclerView dayList;
    private ViewPager viewPager;
    private int start;
    private int end;
    private boolean isCurrentMonth;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public DayListAdapter(ViewPager viewPager, RecyclerView dayList, ArrayList<String> days, boolean isCurrentMonth) {
        this.viewPager = viewPager;
        this.dayList = dayList;
        this.days = days;
        this.isCurrentMonth = isCurrentMonth;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.day.setText(days.get(position));
        if(position < start) {
            holder.dayView.setAlpha(0.5f);
            holder.dayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearDayBackground();
                    viewPager.setCurrentItem(0, true);
                }
            });
        }
        else if(position > end){
            holder.dayView.setAlpha(0.5f);
            holder.dayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearDayBackground();
                    viewPager.setCurrentItem(2, true);
                }
            });
        }
        else {
            holder.dayView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clearDayBackground();
                    holder.selectedDayBackground.setVisibility(View.VISIBLE);
                }
            });
        }

        if(isCurrentMonth) {
            Date today = new Date();
            if(position == today.getDate()+start-1) holder.todayBackground.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    private void clearDayBackground() {
        for(int i=0; i<dayList.getChildCount(); i++) {
            ((ViewHolder)dayList.getChildViewHolder(dayList.getChildAt(i))).selectedDayBackground.setVisibility(View.INVISIBLE);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout dayView;
        ImageView todayBackground;
        ImageView selectedDayBackground;
        TextView day;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayView = itemView.findViewById(R.id.day_view);
            todayBackground = itemView.findViewById(R.id.today_background);
            selectedDayBackground = itemView.findViewById(R.id.selected_day_background);
            day = itemView.findViewById(R.id.day);
        }
    }
}
