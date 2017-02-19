package com.swpuiot.mydrawerlayout2.view.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.model.DiaryEntity;

public class ReadDiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    private TextView lastDay;
    private TextView today;
    private TextView nextDay;
    private TextView readDiaryTitle;
    private TextView readDiaryDate;
    private TextView readDiaryWeekDay;
    private TextView readDiaryWeather;
    private TextView readDiaryContent;

    private DiaryEntity diaryEntityBeen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_read_diary);
        inite();
    }

    public void inite() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_revisediaryactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        readDiaryTitle = (TextView) findViewById(R.id.text_readdiarytitle);
        readDiaryDate = (TextView) findViewById(R.id.text_readdiarydate);
        readDiaryWeekDay = (TextView) findViewById(R.id.text_readdiaryweekday);
        readDiaryWeather = (TextView) findViewById(R.id.text_readdiayweather);
        readDiaryContent = (TextView) findViewById(R.id.text_readdiarycontent);

        lastDay = (TextView) findViewById(R.id.text_gotolastday);
        lastDay.setOnClickListener(this);
        today = (TextView) findViewById(R.id.text_thisday);
        today.setOnClickListener(this);
        nextDay = (TextView) findViewById(R.id.text_gotonextday);
        nextDay.setOnClickListener(this);

        if (getIntent().hasExtra("readDiaryBeen")) {
            diaryEntityBeen = (DiaryEntity) getIntent().getSerializableExtra("readDiaryBeen");
            readDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
            readDiaryDate.setText(diaryEntityBeen.getDiaDate());
            readDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
            readDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
            readDiaryContent.setText(diaryEntityBeen.getDiaContent());
            today.setText(diaryEntityBeen.getDiaDate());
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        
    }
}
