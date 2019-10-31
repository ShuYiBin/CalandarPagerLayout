package com.kshu.calendarpagerlayout;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.LinkedList;

public class MonthPagerAdapter extends PagerAdapter {
    private LinkedList<View> monthViews;

    public LinkedList<View> getMonthViews() {
        return monthViews;
    }

    public MonthPagerAdapter(LinkedList<View> views) {
        monthViews = views;
    }

    @Override
    public int getCount() {
        return monthViews.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object==view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(monthViews.get(position));
        return monthViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        int index = monthViews.indexOf(object);
        if(index == -1)
            return POSITION_NONE;
        else
            return index;
    }
}
