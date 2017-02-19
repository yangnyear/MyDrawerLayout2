package com.swpuiot.mydrawerlayout2.view.controler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swpuiot.mydrawerlayout2.view.model.DiaryEntity;

import java.util.List;

/**
 * Created by 羊荣毅_L on 2017/1/25.
 */
public class DataBaseFunction {
    private SQLiteDatabase db;
    private MyDtaBaseHelper dtaBaseHelper;
    private Context context;
    private List<DiaryEntity> diaryEntityList;

    public DataBaseFunction(Context context) {
        this.context = context;
        dtaBaseHelper = new MyDtaBaseHelper(context, "MyDiary.db", null, 1);
        db = dtaBaseHelper.getWritableDatabase();
    }

    public void save(String name, ContentValues values) {
        if (values!=null)
        db.insert(name, null, values);
        else return;
    }

    public void selectDiary(List<DiaryEntity> diaryEntityList) {
        this.diaryEntityList = diaryEntityList;
        String date, weekday, title, weather,content;
        int year, mouth, day,id;
        Cursor cursor = db.query("Diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id=cursor.getInt(cursor.getColumnIndex("diaID"));
                title = cursor.getString(cursor.getColumnIndex("diaTile"));
                year = cursor.getInt(cursor.getColumnIndex("diaYear"));
                mouth = cursor.getInt(cursor.getColumnIndex("diaMouth"));
                day = cursor.getInt(cursor.getColumnIndex("diaDay"));
                date = year + "年" + mouth + "月" + day + "日";
                weekday=cursor.getString(cursor.getColumnIndex("diaWeekday"));
                weather=cursor.getString(cursor.getColumnIndex("diaWeather"));
                content=cursor.getString(cursor.getColumnIndex("diaContent"));
                diaryEntityList.add(new DiaryEntity(title,date,weekday,weather,year,mouth,day,id,content));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    public void upDateDiary(int id,String title,String weather,String content){
        if (!title.equals("")&&!weather.equals("")&&!content.equals("")){
            String upDateSql="update Diary set diaTile='"+title
                    +"',diaWeather='"+weather
                    +"',diaContent='"+content
                    +"'\n"
                    +"where diaID='"+id
                    +"'";
            db.execSQL(upDateSql);
        }
    }
    public void deletDiary(int id){
        String deleteSQL="delete from Diary\n" +
                "where diaID='"+id+"'";
        db.execSQL(deleteSQL);
    }
}
