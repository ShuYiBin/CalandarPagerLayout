package com.kshu.calendarpagerlayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DayListAdapter extends RecyclerView.Adapter<DayListAdapter.ViewHolder> {
    private ArrayList<String> days;
    private int start;
    private int end;

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public DayListAdapter(ArrayList<String> days) {
        this.days = days;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.day_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.day.setText(days.get(position));
        if(position < start || position > end) {
            holder.day.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
        return days.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView day;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
        }
    }
}
