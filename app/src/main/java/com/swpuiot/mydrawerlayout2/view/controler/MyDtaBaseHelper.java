package com.swpuiot.mydrawerlayout2.view.controler;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 羊荣毅_L on 2017/1/15.
 */
public class MyDtaBaseHelper extends SQLiteOpenHelper {
    private Context mContex;
    public static final String CREATE_DIARY="create table  Diary(\n" +
            "diaID integer  primary key,\n" +
            "diaTile text default '无标题',\n" +
            "diaYear integer not null,\n" +
            "diaMouth integer not null,\n" +
            "diaDay integer not null,\n" +
            "diaWeekday varchar(4) not null,\n" +
            "diaWeather TEXT not null,\n" +
            "diaContent text not null\n" +
            ")";
    public MyDtaBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContex=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
