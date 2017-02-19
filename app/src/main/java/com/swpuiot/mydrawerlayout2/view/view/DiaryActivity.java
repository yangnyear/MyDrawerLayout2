package com.swpuiot.mydrawerlayout2.view.view;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.controler.DataBaseFunction;
import com.swpuiot.mydrawerlayout2.view.controler.MyDtaBaseHelper;

import java.util.Calendar;


public class DiaryActivity extends AppCompatActivity implements View.OnClickListener {
//    private TextView lastDay;
//    private TextView nextDay;
//    private TextView choiseDate;
    private EditText diaTitle;
    private TextView diaDate;
    private TextView diaWeekday;
    private EditText diaWeather;
    private TextView diaContent;
    private TextView save;
    private Toolbar toolbar;

    private AlertDialog.Builder dialog;
    private LayoutInflater inflater;
    private View view;
    private View view1;

    private DatePicker datePicker;

    private int mouth;
    private int day;
    //database
    private MyDtaBaseHelper dtaBaseHelper;
    private DataBaseFunction mDataBase;
    private ContentValues values;

    //Looper
    private int idLooper=0;
    private String titleLooper;
    private int yearLooper;
    private int mouthLooper;
    private int dayLooper;
    private int year;

    private String weekday;
    private String weekdayLooper;
    private String weatherLooper;
    private String contentLooper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_diary);
        initiate();
        getWeekDay();
        if (diaWeekday!=null)
        diaWeekday.setText(weekdayLooper);
    }

    private void initiate() {
        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_diaryactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        save = (TextView) findViewById(R.id.text_save);
        save.setOnClickListener(this);
        diaTitle = (EditText) findViewById(R.id.text_newdiarytitle);
        diaDate = (TextView) findViewById(R.id.text_newdiarydate);
        diaWeekday = (TextView) findViewById(R.id.text_newdiaryweekday);
        diaWeather = (EditText) findViewById(R.id.text_newdiayweather);
        diaContent = (TextView) findViewById(R.id.text_newdiarycontent);
        //时间选择
        dialog = new AlertDialog.Builder(DiaryActivity.this);
        inflater = LayoutInflater.from(DiaryActivity.this);
        //初始化数据库
        dtaBaseHelper = new MyDtaBaseHelper(this, "MyDiary.db", null, 1);
        mDataBase=new DataBaseFunction(this);
        values = new ContentValues();
        dtaBaseHelper.getWritableDatabase();
        view = inflater.inflate(R.layout.mydatepicker, null);
        datePicker = (DatePicker) view.findViewById(R.id.tatepicker_coisetime);
        mouth = datePicker.getMonth() + 1;
        day = datePicker.getDayOfMonth();
        year = datePicker.getYear();
        diaDate.setText(year + "年" + mouth + "月" + day + "日");
        //获得sharedPreferences对象
        sharedPreferences=getSharedPreferences("diaID",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        idLooper=sharedPreferences.getInt("idLooper",idLooper);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.text_save:
                setDiary();

                break;

        }

    }
    public void getWeekDay(){
        final Calendar c = Calendar.getInstance();
        weekday= String.valueOf(c.get(Calendar.DAY_OF_WEEK));
            if ("1".equals(weekday)){
                weekdayLooper="周天";
            }else if ("2".equals(weekday)){
                weekdayLooper="周一";
            }else if ("3".equals(weekday)){
                weekdayLooper="周二";
            }else if ("4".equals(weekday)){
                weekdayLooper="周三";
            }else if ("5".equals(weekday)){
                weekdayLooper="周四";
            }else if ("6".equals(weekday)){
                weekdayLooper="周五";
            }else if ("7".equals(weekday)){
                weekdayLooper="周六";
            }

    }

    public void setDiary() {
        if (diaContent.getText().toString().equals("")) {
            Toast.makeText(DiaryActivity.this, "亲！还没写内容呢", Toast.LENGTH_SHORT).show();
            return;
        }
        if (diaWeather.getText() .toString().equals("")) {
            Toast.makeText(DiaryActivity.this, "亲！填一下天气吧", Toast.LENGTH_SHORT).show();
            return;
        }
        if (diaTitle.getText().toString().equals("")) {
            titleLooper = "无标题";
            idLooper = ++idLooper;

            editor.putInt("idLooper",idLooper);
            editor.commit();

            yearLooper = year;
            mouthLooper = mouth;
            dayLooper = day;
            weatherLooper = diaWeather.getText().toString();
            contentLooper = diaContent.getText().toString();
            values.put("diaID", idLooper);
            values.put("diaTile", titleLooper);
            values.put("diaYear", yearLooper);
            values.put("diaMouth", mouthLooper);
            values.put(" diaDay", dayLooper);
            values.put("diaWeekday ", weekdayLooper);
            values.put("diaWeather", weatherLooper);
            values.put("diaContent", contentLooper);
            mDataBase.save("Diary", values);
            Toast.makeText(DiaryActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            finish();
        } else {
            titleLooper = diaTitle.getText().toString();
            idLooper = ++idLooper;

            editor.putInt("idLooper",idLooper);
            editor.commit();

            yearLooper = year;
            mouthLooper = mouth;
            dayLooper = day;
            weatherLooper = diaWeather.getText().toString();
            contentLooper = diaContent.getText().toString();
            values.put("diaID", idLooper);
            values.put("diaTile", titleLooper);
            values.put("diaYear", yearLooper);
            values.put("diaMouth", mouthLooper);
            values.put(" diaDay", dayLooper);
            values.put("diaWeekday ", weekdayLooper);
            values.put("diaWeather", weatherLooper);
            values.put("diaContent", contentLooper);
            mDataBase.save("Diary", values);
            Toast.makeText(DiaryActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}







