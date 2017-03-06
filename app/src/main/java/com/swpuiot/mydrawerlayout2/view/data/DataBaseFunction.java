package com.swpuiot.mydrawerlayout2.view.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.swpuiot.mydrawerlayout2.view.entities.DiaryEntity;

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

    public void save(Object[] values) {
        String insetSQL="insert into Diary(diaID,diaTile,diaYear,diaMouth,diaDay,diaWeekday,diaWeather,diaContent)" +
                " values(?,?,?,?,?,?,?,?)";
        if (values != null)
            db.execSQL(insetSQL, values);
        else return;
    }

    //查询所有
    public void selectDiary(List<DiaryEntity> diaryEntityList) {
        this.diaryEntityList = diaryEntityList;
        String date, weekday, title, weather, content;
        int year, mouth, day, id;
        Cursor cursor = db.query("Diary", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("diaID"));
                title = cursor.getString(cursor.getColumnIndex("diaTile"));
                year = cursor.getInt(cursor.getColumnIndex("diaYear"));
                mouth = cursor.getInt(cursor.getColumnIndex("diaMouth"));
                day = cursor.getInt(cursor.getColumnIndex("diaDay"));
                date = year + "年" + mouth + "月" + day + "日";
                weekday = cursor.getString(cursor.getColumnIndex("diaWeekday"));
                weather = cursor.getString(cursor.getColumnIndex("diaWeather"));
                content = cursor.getString(cursor.getColumnIndex("diaContent"));
                diaryEntityList.add(new DiaryEntity(title, date, weekday, weather, year, mouth, day, id, content));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    public void upDateDiary(int id, Object[] values) {
        if (values!=null) {
            String upDateSql = "update Diary set diaTile=?"
                    + ",diaWeather=?"
                    + ",diaContent=?"
                    + "\n"
                    + "where diaID='" + id
                    + "'";
            db.execSQL(upDateSql,values);
        }
    }

    public void deletDiary(int id) {
        String deleteSQL = "delete from Diary\n" +
                "where diaID='" + id + "'";
        db.execSQL(deleteSQL);
    }
    //按日期查询
    public DiaryEntity selectByDate(int year,int mouth,int day){
        DiaryEntity  diaryEntity = null;
        String date, weekday, title, weather, content;
        int id;
        String selectByDate="select Diary.diaID, Diary.diaTile,Diary.diaYear,Diary.diaMouth,Diary.diaDay,Diary.diaWeekday,Diary.diaWeather,Diary.diaContent from Diary\n" +
                "where Diary.diaYear=? and Diary.diaMouth=? and Diary.diaDay=?";
        String[] valu={year+"",mouth+"",day+""};
        Cursor cursor =db.rawQuery(selectByDate,valu);
        if (cursor.moveToFirst()) {
            do {
        id = 0;
        title = cursor.getString(cursor.getColumnIndex("diaTile"));
        date = year + "年" + mouth + "月" + day + "日";
        weekday = cursor.getString(cursor.getColumnIndex("diaWeekday"));
        weather = cursor.getString(cursor.getColumnIndex("diaWeather"));
        content = cursor.getString(cursor.getColumnIndex("diaContent"));
                diaryEntity=new DiaryEntity(title, date, weekday, weather, year, mouth, day, id, content);
            } while (cursor.moveToNext());
        }
        return diaryEntity;
    }
    public int maxDayOfMouth(int year,int mouth){
        int maxDay = 0;
        switch(mouth){
            case 1:
            case 3:
            case 5:
            case 7:
            case 9:
            case 11:
                maxDay=31;
                break;
            case 4:
            case 6:
            case 8:
            case 10:
            case 12:
                maxDay=30;
                break;
            case 2:
                if((year%4==0&&year%100!=0)||(year%400==0)){
                    maxDay=29;
                }else maxDay=28;
        }
        return maxDay;
    }

}
