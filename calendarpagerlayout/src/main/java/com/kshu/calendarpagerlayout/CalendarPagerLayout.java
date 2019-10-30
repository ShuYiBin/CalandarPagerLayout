package com.kshu.calendarpagerlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class CalendarPagerLayout extends LinearLayout{
    boolean hasMonthPicker;
    CalendarMonthPicker mMonthPicker;
    boolean hasDailyList;

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
        hasMonthPicker = typedArray.getBoolean(R.styleable.CalendarPagerLayout_has_month_picker, true);
        hasDailyList = typedArray.getBoolean(R.styleable.CalendarPagerLayout_has_daily_list, true);
        typedArray.recycle();
    }

    private void setupView() {
        setOrientation(VERTICAL);
        if(hasMonthPicker) {
            mMonthPicker = (CalendarMonthPicker) LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker, (ViewGroup)getRootView(), false);
            addView(mMonthPicker);
        }

        if(hasDailyList) {

        }
    }
}
