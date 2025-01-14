package com.groupsix.importexcel;

import java.util.List;

public class ExcelHelper {
    // get format dd/MM/yyyy HH:mm:ss
    public static String getFormatDate(String timestamp) throws Exception {
        try {
            String[] arr = timestamp.split("\s", 2);
            String[] date = arr[0].split("/");
            String[] time = arr[1].split(":");
            String hour = time[0];
            String minute = time[1];
            String second = time.length == 3 ? time[2] : "00";
            return date[2] + "/" + date[1] + "/" + date[0] + " " + hour + ":" + minute + ":" + second;
        }
        catch (Exception e) {
            throw new Exception("Lỗi dữ liệu thời gian");
        }
    }

    public static void exportExcel(List<Object> objects, String hello) {
    }
}
