package com.swpuiot.mydrawerlayout2.view.entities;

/**
 * Created by 羊荣毅_L on 2017/1/15.
 */
public class DiaryIInformationEntity {
    private String diaryTitle;
    private String date;
    private String wenkday;
    private String weather;
    private int diaId;

    public DiaryIInformationEntity(String diaryTitle, String date, String wenkday, String weather, int diaId) {
        this.diaryTitle = diaryTitle;
        this.date = date;
        this.wenkday = wenkday;
        this.weather = weather;
        this.diaId = diaId;
    }

    public int getDiaId() {
        return diaId;
    }

    public void setDiaId(int diaId) {
        this.diaId = diaId;
    }

    public String getDiaryTitle() {
        return diaryTitle;
    }

    public void setDiaryTitle(String diaryTitle) {
        this.diaryTitle = diaryTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWenkday() {
        return wenkday;
    }

    public void setWenkday(String wenkday) {
        this.wenkday = wenkday;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }
}
