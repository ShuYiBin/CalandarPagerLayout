package com.kshu.calendarpager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.kshu.calendarpagerlayout.CalendarPagerLayout;
import com.kshu.calendarpagerlayout.DayListener;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View root = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
        setContentView(root);

        CalendarPagerLayout c = findViewById(R.id.calendar);
        c.setDayListener(new DayListener() {
            @Override
            public void onDayClick(Date date) {
                Snackbar.make(root, date.toString(), Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}
