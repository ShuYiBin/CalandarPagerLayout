package com.kshu.calendarpagerlayout;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MonthPagerChangeListener implements ViewPager.OnPageChangeListener {
    MonthView monthView;
    PagerAdapter pagerAdapter;

    public MonthPagerChangeListener(MonthView monthView, MonthPagerAdapter pagerAdapter) {
        this.monthView = monthView;
        this.pagerAdapter = pagerAdapter;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            monthView.lastMonth();
        }
        if (position == 2) {
            monthView.nextMonth();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            pagerAdapter.notifyDataSetChanged();
        }
    }
}
