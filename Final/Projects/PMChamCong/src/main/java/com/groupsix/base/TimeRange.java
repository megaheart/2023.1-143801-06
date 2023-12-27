package com.groupsix.base;

public class TimeRange {
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(int monthCount) {
        this.monthCount = monthCount;
    }

    private int month;
    private int year;
    private int monthCount;

    public TimeRange(int month, int year, int monthCount) {
        this.month = month;
        this.year = year;
        this.monthCount = monthCount;
    }
}
