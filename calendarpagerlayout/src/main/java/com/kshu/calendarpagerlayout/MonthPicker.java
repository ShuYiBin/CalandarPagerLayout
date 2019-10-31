package com.kshu.calendarpagerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MonthPicker extends RelativeLayout {
    ViewPager monthPager;
    MonthPagerAdapter monthPagerAdapter;
    LinkedList<View> monthViews;
    Date current;
    SimpleDateFormat monthString = new SimpleDateFormat("MMMM yyyy", Locale.US);

    public MonthPicker(Context context) {
        super(context);
    }

    public MonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupView(Date today) {
        monthPager = findViewById(R.id.month_pager);
        current = today;
        Calendar calendar = Calendar.getInstance();

        View currentMonth = LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker_view, (ViewGroup)getRootView(), false);
        calendar.setTime(today);
        ((TextView)currentMonth.findViewById(R.id.month_text)).setText(monthString.format(calendar.getTime()));

        View lastMonth = LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker_view, (ViewGroup)getRootView(), false);
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, -1);
        ((TextView)lastMonth.findViewById(R.id.month_text)).setText(monthString.format(calendar.getTime()));

        View nextMonth = LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker_view, (ViewGroup)getRootView(), false);
        calendar.setTime(today);
        calendar.add(Calendar.MONTH, 1);
        ((TextView)nextMonth.findViewById(R.id.month_text)).setText(monthString.format(calendar.getTime()));

        monthViews = new LinkedList<>();
        monthViews.add(lastMonth);
        monthViews.add(currentMonth);
        monthViews.add(nextMonth);

        monthPagerAdapter = new MonthPagerAdapter(monthViews);
        monthPager.setAdapter(monthPagerAdapter);
        monthPager.setCurrentItem(1, true);

        monthPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    lastMonth();
                }
                if (position == 2) {
                    nextMonth();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    monthPagerAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void lastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        current = calendar.getTime();

        View lastMonth = LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        ((TextView)lastMonth.findViewById(R.id.month_text)).setText(monthString.format(calendar.getTime()));

        monthPagerAdapter.getMonthViews().addFirst(lastMonth);
        monthPagerAdapter.getMonthViews().removeLast();
    }

    public void nextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        current = calendar.getTime();

        View nextMonth = LayoutInflater.from(getContext()).inflate(R.layout.calendar_month_picker_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        ((TextView)nextMonth.findViewById(R.id.month_text)).setText(monthString.format(calendar.getTime()));

        monthPagerAdapter.getMonthViews().addLast(nextMonth);
        monthPagerAdapter.getMonthViews().removeFirst();
    }
}