package com.swpuiot.mydrawerlayout2.view.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.data.DataBaseFunction;
import com.swpuiot.mydrawerlayout2.view.entities.DiaryEntity;

public class ReviseDiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;

    private TextView thisDiaryTitle;
    private TextView thisDiaryDate;
    private TextView thisDiaryWeekDay;
    private TextView thisDiaryWeather;
    private TextView thisDiaryContent;
    private TextView okToRevise;

    private DiaryEntity diaryEntityBeen;
    private DataBaseFunction mDatabase;

    private String title;
    private String weather;
    private String content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_revise_diary);
        inite();
    }

    public void inite() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_revisediaryactivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        thisDiaryTitle = (TextView) findViewById(R.id.text_revisediarytitle);
        thisDiaryDate = (TextView) findViewById(R.id.text_revisediarydate);
        thisDiaryWeekDay = (TextView) findViewById(R.id.text_revisediaryweekday);
        thisDiaryWeather = (TextView) findViewById(R.id.text_revisediayweather);
        thisDiaryContent = (TextView) findViewById(R.id.text_revisediarycontent);

        okToRevise = (TextView) findViewById(R.id.text_savereviseresult);
        okToRevise.setOnClickListener(this);

        mDatabase = new DataBaseFunction(this);


        if (getIntent().hasExtra("DiaryBeen")) {
            diaryEntityBeen = (DiaryEntity) getIntent().getSerializableExtra("DiaryBeen");
            thisDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
            thisDiaryDate.setText(diaryEntityBeen.getDiaDate());
            thisDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
            thisDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
            thisDiaryContent.setText(diaryEntityBeen.getDiaContent());
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_savereviseresult:
                update();
                break;
        }
    }

    //更新数据
    public void update() {
        title = thisDiaryTitle.getText().toString();
        weather = thisDiaryWeather.getText().toString();
        content = thisDiaryContent.getText().toString();
        if (title.equals(""))
            title = "无标题";
        if (weather.equals("")) {
            Toast.makeText(ReviseDiaryActivity.this, "亲！写一下天气吧!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (content.equals("")) {
            Toast.makeText(ReviseDiaryActivity.this, "内容都没有!", Toast.LENGTH_SHORT).show();
            return;
        }
        Object[] values = new Object[]{title, weather, content};
        mDatabase.upDateDiary(diaryEntityBeen.getDiaId(), values);
        Toast.makeText(ReviseDiaryActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();
    }
}
