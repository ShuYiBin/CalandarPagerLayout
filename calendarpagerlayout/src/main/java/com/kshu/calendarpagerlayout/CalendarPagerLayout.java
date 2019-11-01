package com.kshu.calendarpagerlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Date;

public class CalendarPagerLayout extends LinearLayout{
    boolean hasDailyList;
    String monthFormat;
    Date today;

    MonthView monthView;

    public CalendarPagerLayout(Context context) {
        super(context);
        setupView();
    }

    public CalendarPagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupAttrs(attrs);
        setupView();
    }

    public CalendarPagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupAttrs(attrs);
        setupView();
    }

    private void setupAttrs(@Nullable AttributeSet set){
        if (set == null) return;
        TypedArray typedArray = getContext().obtainStyledAttributes(set, R.styleable.CalendarPagerLayout);
        hasDailyList = typedArray.getBoolean(R.styleable.CalendarPagerLayout_has_daily_list, true);
        monthFormat = typedArray.getString(R.styleable.CalendarPagerLayout_month_format);
        if(monthFormat==null || monthFormat.isEmpty()) monthFormat = "MMMM yyyy";

        today = new Date();
        typedArray.recycle();
    }

    public void setupView() {
        setOrientation(VERTICAL);
        monthView = (MonthView) LayoutInflater.from(getContext()).inflate(R.layout.month_view, (ViewGroup)getRootView(), false);
        monthView.setupView(today, monthFormat);
        addView(monthView);

        if(hasDailyList) {

        }
    }
}
