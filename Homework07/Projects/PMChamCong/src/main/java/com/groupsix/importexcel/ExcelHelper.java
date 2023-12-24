package com.groupsix.importexcel;

public class ExcelHelper {
    // get format dd/MM/yyyy HH:mm:ss
    public static String getFormatDate(String timestamp) {
        String[] arr = timestamp.split(" ");
        String[] date = arr[0].split("/");
        String[] time = arr[1].split(":");
        return date[2] + "/" + date[1] + "/" + date[0] + " " + time[0] + ":" + time[1] + ":" + time[2];
    }

    public static int getHour(String timestamp) {
        String[] arr = timestamp.split(" ");
        String[] time = arr[1].split(":");
        return Integer.parseInt(time[0]);
    }

    public static int getMinute(String timestamp) {
        String[] arr = timestamp.split(" ");
        String[] time = arr[1].split(":");
        return Integer.parseInt(time[1]);
    }
}
