package com.dqztc.yourbirthday;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.dqztc.yourbirthday.pictimeview.MyDatePicker;

import java.util.Calendar;

public class ScrollingActivity extends AppCompatActivity {

    private TextView showText;
    private SpannableStringBuilder stringBuilder;
    private LunarCalendar lunarCalendar;
    private boolean showAllDates;

    private String selectStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                int startYear = 1989;
//                int startmonth = 7;
//                int startday = 4;
//
//                getYourBirthday(startYear, startmonth, startday);
                onYearMonthDayPicker("选择开始日期", selectStr, "1980-01-01", "");

            }
        });

        showText = findViewById(R.id.date_time);
        stringBuilder = new SpannableStringBuilder();
        lunarCalendar = new LunarCalendar();

        int startYear = 1989;
        int startmonth = 7;
        int startday = 4;

        getYourBirthday(startYear, startmonth, startday);
    }

    private void getYourBirthday(int startYear, int startmonth, int startday) {
        String birthDay = "";
        stringBuilder.clear();
        stringBuilder.append("起止年份：" + startYear + "——" + (startYear + 100) + "\n\n");
        for (int i = 0; i <= 100; i++) {
            int day = startday;
            if (i < 4) {
                day++;
            }

            String date = lunarCalendar.getLunarString(startYear + i, startmonth, day);
            if (i == 0) {
                birthDay = date;
            }
            if (date.equals(birthDay) || showAllDates) {
//                stringBuilder.append("年龄" + i + "阳历：" + (startYear + i) + "年7月4日 =>> ");
//                stringBuilder.append(date);

                String content = i + "岁 阳历：" + (startYear + i) + "年" + startmonth + "月" + startday + "日 =>> " + date;
                //文本的颜色
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.YELLOW);

                SpannableString spannableString = new SpannableString(content);
                spannableString.setSpan(colorSpan, 0, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                stringBuilder.append(date.equals(birthDay) ? spannableString + "***" : content);
                stringBuilder.append("\n");
                stringBuilder.append("\n");
            }
        }
        showText.setText(stringBuilder);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showText.setText("");
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_show) {
            showAllDates = true;
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_hide) {
            showAllDates = false;
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //--选择年月日时间 -- start
    public void onYearMonthDayPicker(String title, String selectTime, String startTime, String endTime) {
        Calendar currentDayCalendar;    //截止时间
        if (false == TextUtils.isEmpty(selectTime)) {
            currentDayCalendar = BaseUtil.TransCalendar2(selectTime);
        } else {
            currentDayCalendar = Calendar.getInstance();

            currentDayCalendar.set(Calendar.HOUR_OF_DAY, 0);
            currentDayCalendar.set(Calendar.MINUTE, 0);
            currentDayCalendar.set(Calendar.SECOND, 0);
        }


        int year = currentDayCalendar.get(Calendar.YEAR);
        int month = currentDayCalendar.get(Calendar.MONTH) + 1;
        int day = currentDayCalendar.get(Calendar.DAY_OF_MONTH);

        final MyDatePicker picker = new MyDatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        if (false == TextUtils.isEmpty(title)) {
            picker.setTitleText(title);
        }
        picker.setTopPadding(BaseUtil.dip2px(this, 10));

        if (TextUtils.isEmpty(endTime)) {
            Calendar today = Calendar.getInstance();

            picker.setRangeEnd(today.get(Calendar.YEAR), today.get(Calendar.MONTH) + 1, today.get(Calendar.DAY_OF_MONTH));
        } else {
            Calendar endCalendar = null;
            if (endTime.length() == 10) {
                endCalendar = BaseUtil.TransCalendar2(endTime);
            } else if (endTime.length() == 19) {
                endCalendar = BaseUtil.TransCalendar3(endTime);
            }
            if (null != endCalendar) {
                picker.setRangeEnd(endCalendar.get(Calendar.YEAR), endCalendar.get(Calendar.MONTH) + 1, endCalendar.get(Calendar.DAY_OF_MONTH));

                if (currentDayCalendar.getTime().getTime() > endCalendar.getTime().getTime()) {
                    year = endCalendar.get(Calendar.YEAR);
                    month = endCalendar.get(Calendar.MONTH) + 1;
                    day = endCalendar.get(Calendar.DAY_OF_MONTH);
                }
            }
        }
        if (TextUtils.isEmpty(startTime)) {

            picker.setRangeStart(year - 50, 1, 1);
        } else {
            Calendar startCalendar = BaseUtil.getCalendarFromTimeStr(startTime);
            picker.setRangeStart(startCalendar.get(Calendar.YEAR), startCalendar.get(Calendar.MONTH) + 1, startCalendar.get(Calendar.DAY_OF_MONTH));

            if (currentDayCalendar.getTime().getTime() < startCalendar.getTime().getTime()) {
                year = startCalendar.get(Calendar.YEAR);
                month = startCalendar.get(Calendar.MONTH) + 1;
                day = startCalendar.get(Calendar.DAY_OF_MONTH);
            }
        }

        picker.setSelectedItem(year, month, day);
        picker.setResetWhileWheel(false);
        picker.setOnDatePickListener(new MyDatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {

                selectStr = year + "-" + month + "-" + day;

                getYourBirthday(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
            }
        });

        picker.show();
    }


}
