package com.result.details;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.result.R;
import com.result.tools.FirstEvent_Rili;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * autour: 刘东东
 * date: 2016/12/18 18:51 
 * update: 2016/12/18
 */
public class CalendarActivity extends AppCompatActivity implements View.OnClickListener,OnDateSelectedListener, OnMonthChangedListener {
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    @Bind(R.id.cale_toolBar)
    Toolbar caleToolBar;
    @Bind(R.id.view)
    MaterialCalendarView view;
    @Bind(R.id.cale_btn)
    Button caleBtn;
    private int year;
    private int day;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        //设置点击选择日期改变事件
        view.setOnDateChangedListener(this);
        //设置滑动选择改变月份事件
        view.setOnMonthChangedListener(this);


        caleToolBar.setTitle("选择日期");
        caleToolBar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(caleToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//返回箭头
        caleToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        caleBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EventBus.getDefault().postSticky(new FirstEvent_Rili(year,day,month));
        finish();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        month = date.getMonth();
        day = date.getDay();
        year = date.getYear();

        Log.e("TAG", "onEventMainThread: ++---------------------------"+year+day);

    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(this, FORMATTER.format(date.getDate()), Toast.LENGTH_SHORT).show();
    }
}
