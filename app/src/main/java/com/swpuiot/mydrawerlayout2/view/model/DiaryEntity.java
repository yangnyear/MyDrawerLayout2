package com.swpuiot.mydrawerlayout2.view.model;

import java.io.Serializable;

/**
 * Created by 羊荣毅_L on 2017/1/30.
 */
public class DiaryEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String diaTitle;
    private String diaDate;
    private String diaWenkday;
    private String diaWeather;
    private int year;
    private int mouth;
    private int day;
    private int diaId;
    private String diaContent;

    public DiaryEntity(String diaTitle, String diaDate, String diaWenkday, String diaWeather, int year, int mouth, int day, int diaId, String diaContent) {
        this.diaTitle = diaTitle;
        this.diaDate = diaDate;
        this.diaWenkday = diaWenkday;
        this.diaWeather = diaWeather;
        this.year = year;
        this.mouth = mouth;
        this.day = day;
        this.diaId = diaId;
        this.diaContent = diaContent;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDiaTitle() {
        return diaTitle;
    }

    public void setDiaTitle(String diaTitle) {
        this.diaTitle = diaTitle;
    }

    public String getDiaDate() {
        return diaDate;
    }

    public void setDiaDate(String diaDate) {
        this.diaDate = diaDate;
    }

    public String getDiaWenkday() {
        return diaWenkday;
    }

    public void setDiaWenkday(String diaWenkday) {
        this.diaWenkday = diaWenkday;
    }

    public String getDiaWeather() {
        return diaWeather;
    }

    public void setDiaWeather(String diaWeather) {
        this.diaWeather = diaWeather;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMouth() {
        return mouth;
    }

    public void setMouth(int mouth) {
        this.mouth = mouth;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getDiaId() {
        return diaId;
    }

    public void setDiaId(int diaId) {
        this.diaId = diaId;
    }

    public String getDiaContent() {
        return diaContent;
    }

    public void setDiaContent(String diaContent) {
        this.diaContent = diaContent;
    }
}
