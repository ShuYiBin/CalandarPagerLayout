package com.kshu.calendarpagerlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;

public class MonthView extends RelativeLayout {
    //Attrs
    Date current;
    SimpleDateFormat monthFormat;

    //View
    ImageView lastMonthBtn;
    ImageView nextMonthBtn;
    ViewPager viewPager;
    MonthPagerAdapter pagerAdapter;
    MonthPagerChangeListener pagerChangeListener;
    View lastMonth;
    View currentMonth;
    View nextMonth;
    LinkedList<View> monthViews;

    public View getCurrentMonth() {
        return currentMonth;
    }

    public MonthView(Context context) {
        super(context);
    }

    public MonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setupView(Date today, String monthFormat) {
        current = today;
        this.monthFormat = new SimpleDateFormat(monthFormat, Locale.US);

        setupMonthPicker();
        setupDayView();

        pagerAdapter = new MonthPagerAdapter(monthViews);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(1, true);
        pagerChangeListener = new MonthPagerChangeListener(this, pagerAdapter);
        viewPager.addOnPageChangeListener(pagerChangeListener);

    }

    private void setupMonthPicker() {
        lastMonthBtn = findViewById(R.id.last_month);
        lastMonthBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0, true);
            }
        });

        nextMonthBtn = findViewById(R.id.next_month);
        nextMonthBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2, true);
            }
        });

        viewPager = findViewById(R.id.month_pager);

        Calendar calendar = Calendar.getInstance();

        lastMonth = LayoutInflater.from(getContext()).inflate(R.layout.month_pager_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        ((TextView)lastMonth.findViewById(R.id.month_text)).setText(monthFormat.format(calendar.getTime()));

        currentMonth = LayoutInflater.from(getContext()).inflate(R.layout.month_pager_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        ((TextView)currentMonth.findViewById(R.id.month_text)).setText(monthFormat.format(calendar.getTime()));

        nextMonth = LayoutInflater.from(getContext()).inflate(R.layout.month_pager_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        ((TextView)nextMonth.findViewById(R.id.month_text)).setText(monthFormat.format(calendar.getTime()));

        monthViews = new LinkedList<>();
        monthViews.add(lastMonth);
        monthViews.add(currentMonth);
        monthViews.add(nextMonth);
    }

    private void setupDayView() {
        //Last
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        newDayView(lastMonth, calendar);

        //Current
        calendar = Calendar.getInstance();
        calendar.setTime(current);
        newDayView(currentMonth, calendar);

        //Next
        calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        newDayView(nextMonth, calendar);
    }

    public void newDayView(View monthView, Calendar calendar) {
        RecyclerView dayList = monthView.findViewById(R.id.day_list);
        dayList.setLayoutManager(new GridLayoutManager(getContext(), 7));
        ArrayList<String> days = new ArrayList<>();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayofWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int dayofMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        calendar.add(Calendar.MONTH, -1);
        int dayofLastMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for(int i=dayofWeek; i>1; i--) {
            days.add(String.valueOf(dayofLastMonth+2-i));
        }
        for(int i=0; i<dayofMonth; i++) {
            days.add(String.valueOf(i+1));
        }
        int i = 1;
        if(days.size()>35) {
            while(days.size()<42) {
                days.add(String.valueOf(i));
                i++;
            }
        }
        else {
            while (days.size() < 35) {
                days.add(String.valueOf(i));
                i++;
            }
        }

        DayListAdapter dayListAdapter = new DayListAdapter(days);
        dayListAdapter.setStart(dayofWeek-1);
        dayListAdapter.setEnd(dayofWeek+dayofMonth-2);
        dayList.setAdapter(dayListAdapter);
    }

    public void lastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        current = calendar.getTime();

        View newLastMonth = LayoutInflater.from(getContext()).inflate(R.layout.month_pager_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, -1);
        ((TextView)newLastMonth.findViewById(R.id.month_text)).setText(monthFormat.format(calendar.getTime()));
        newDayView(newLastMonth, calendar);

        View temp = currentMonth;
        currentMonth = lastMonth;
        nextMonth = temp;
        lastMonth = newLastMonth;

        pagerAdapter.getViews().addFirst(newLastMonth);
        pagerAdapter.getViews().removeLast();
    }

    public void nextMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        current = calendar.getTime();

        View newNextMonth = LayoutInflater.from(getContext()).inflate(R.layout.month_pager_view, (ViewGroup)getRootView(), false);
        calendar.setTime(current);
        calendar.add(Calendar.MONTH, 1);
        ((TextView)newNextMonth.findViewById(R.id.month_text)).setText(monthFormat.format(calendar.getTime()));
        newDayView(newNextMonth, calendar);

        View temp = currentMonth;
        currentMonth = nextMonth;
        nextMonth = newNextMonth;
        lastMonth = temp;

        pagerAdapter.getViews().addLast(newNextMonth);
        pagerAdapter.getViews().removeFirst();
    }
}