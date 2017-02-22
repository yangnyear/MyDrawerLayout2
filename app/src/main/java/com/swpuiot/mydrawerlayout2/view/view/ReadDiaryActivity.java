package com.swpuiot.mydrawerlayout2.view.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.TextView;

import com.swpuiot.mydrawerlayout2.R;
import com.swpuiot.mydrawerlayout2.view.controler.DataBaseFunction;
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

    private AlertDialog.Builder dateDialog;
    private LayoutInflater inflater;
    private View dateView,pwView;
    private DatePicker datePicker,pwDatepicker;
    private DataBaseFunction mDataBase;
    private int year, mouth, day;
    private int maxDayOfMouth;


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

        inflater = LayoutInflater.from(ReadDiaryActivity.this);
        mDataBase = new DataBaseFunction(this);

//        pwView= inflater.inflate(R.layout.mydatepicker, null);
//        pwDatepicker= (DatePicker) pwView.findViewById(R.id.tatepicker_coisetime);


        if (getIntent().hasExtra("readDiaryBeen")) {
            diaryEntityBeen = (DiaryEntity) getIntent().getSerializableExtra("readDiaryBeen");
            if (diaryEntityBeen!=null){
                readDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
                readDiaryDate.setText(diaryEntityBeen.getDiaDate());
                readDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
                readDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
                readDiaryContent.setText(diaryEntityBeen.getDiaContent());
                today.setText(diaryEntityBeen.getDiaDate());
            }
        }else if (getIntent().hasExtra("date")){
            int[] date=getIntent().getIntArrayExtra("date");
            if (date!=null){
                year=date[0];
                mouth=date[1];
                day=date[2];
                diaryEntityBeen = mDataBase.selectByDate(year, mouth, day);
                if (diaryEntityBeen != null) {
                    readDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
                    readDiaryDate.setText(diaryEntityBeen.getDiaDate());
                    readDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
                    readDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
                    readDiaryContent.setText(diaryEntityBeen.getDiaContent());
                    today.setText(diaryEntityBeen.getDiaDate());
                }
                else {
                    today.setText(year + "年" + mouth + "月" + day + "日");
                    readDiaryTitle.setText("");
                    readDiaryDate.setText("");
                    readDiaryWeekDay.setText("");
                    readDiaryWeather.setText("");
                    readDiaryContent.setText("");
                }
            }
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
            case R.id.text_gotolastday:
                if (day==0&&year==0&&mouth==0){
                    year = diaryEntityBeen.getYear();
                    mouth = diaryEntityBeen.getMouth();
                    day= diaryEntityBeen.getDay()-1 ;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                }else if (day==1&&mouth>1){
                  --mouth;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                    day=maxDayOfMouth;
                }else if (day==1&mouth==1){
                    mouth=12;
                    day=31;
                    --year;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                }else if (day>1&mouth>=1){
                    --day;
                }
                diaryEntityBeen = mDataBase.selectByDate(year, mouth, day);
                if (diaryEntityBeen != null) {
                    readDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
                    readDiaryDate.setText(diaryEntityBeen.getDiaDate());
                    readDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
                    readDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
                    readDiaryContent.setText(diaryEntityBeen.getDiaContent());
                    today.setText(diaryEntityBeen.getDiaDate());
                }
                else {
                   today.setText(year + "年" + mouth + "月" + day + "日");
                    readDiaryTitle.setText("");
                    readDiaryDate.setText("");
                    readDiaryWeekDay.setText("");
                    readDiaryWeather.setText("");
                    readDiaryContent.setText("");
                }
                break;
            case R.id.text_thisday:
                dateView = inflater.inflate(R.layout.mydatepicker, null);
                datePicker = (DatePicker) dateView.findViewById(R.id.tatepicker_coisetime);

                dateDialog = new AlertDialog.Builder(ReadDiaryActivity.this)
                        .setView(dateView)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DiaryEntity selectDiaryEntity;
                               int mouth = datePicker.getMonth() + 1;
                               int day = datePicker.getDayOfMonth();
                               int year = datePicker.getYear();
                                selectDiaryEntity = mDataBase.selectByDate(year, mouth, day);
                                if (selectDiaryEntity != null) {
                                    readDiaryTitle.setText(selectDiaryEntity.getDiaTitle());
                                    readDiaryDate.setText(selectDiaryEntity.getDiaDate());
                                    readDiaryWeekDay.setText(selectDiaryEntity.getDiaWenkday());
                                    readDiaryWeather.setText(selectDiaryEntity.getDiaWeather());
                                    readDiaryContent.setText(selectDiaryEntity.getDiaContent());
                                    today.setText(year + "年" + mouth + "月" + day + "日");
                                }else{
                                    readDiaryTitle.setText("");
                                    readDiaryDate.setText("");
                                    readDiaryWeekDay.setText("");
                                    readDiaryWeather.setText("");
                                    readDiaryContent.setText("");
                                    today.setText(year + "年" + mouth + "月" + day + "日");
                                }
                            }

                        });
                dateDialog.show();
                break;
            case R.id.text_gotonextday:;
                maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                if (day==0&&year==0&&mouth==0){
                    year = diaryEntityBeen.getYear();
                    mouth = diaryEntityBeen.getMouth();
                    day= diaryEntityBeen.getDay()+1 ;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                }else if (day==maxDayOfMouth&&mouth<12){
                    ++mouth;
                    day=1;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                }else if (day==31&&mouth==12){
                    mouth=1;
                    day=1;
                    ++year;
                    maxDayOfMouth=mDataBase.maxDayOfMouth(year, mouth);
                }else if (day<maxDayOfMouth){
                    ++day;
                }
                diaryEntityBeen = mDataBase.selectByDate(year, mouth, day);
                if (diaryEntityBeen != null) {
                    readDiaryTitle.setText(diaryEntityBeen.getDiaTitle());
                    readDiaryDate.setText(diaryEntityBeen.getDiaDate());
                    readDiaryWeekDay.setText(diaryEntityBeen.getDiaWenkday());
                    readDiaryWeather.setText(diaryEntityBeen.getDiaWeather());
                    readDiaryContent.setText(diaryEntityBeen.getDiaContent());
                    today.setText(diaryEntityBeen.getDiaDate());
                }else {
                    today.setText(year + "年" + mouth + "月" + day + "日");
                    readDiaryTitle.setText("");
                    readDiaryDate.setText("");
                    readDiaryWeekDay.setText("");
                    readDiaryWeather.setText("");
                    readDiaryContent.setText("");
                }
                break;
        }
    }
}
