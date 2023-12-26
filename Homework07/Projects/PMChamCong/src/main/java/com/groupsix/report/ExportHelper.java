package com.groupsix.report;

import com.groupsix.importexcel.ImportHistory;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExportHelper<T> {
    public void exportExcel(List<T> list, String nameSheet, String fileName) throws IllegalAccessException, IOException {
        // Blank workbook
        XSSFWorkbook workbook = new XSSFWorkbook();
        // Creating a blank Excel sheet
        XSSFSheet sheet = workbook.createSheet(nameSheet);
        Field[] fields = ImportHistory.class.getDeclaredFields();
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        String[] fieldNames = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            fieldNames[i] = fields[i].getName();
        }
        data.put("1", fieldNames);
        for(int i = 0; i < list.size(); i++){
            Object[] arr = new String[fields.length];
            Object obj = list.get(i);
            for(int j = 0; j < fields.length; j++){
                fields[j].setAccessible(true);
                arr[j] = String.valueOf(fields[j].get(obj));
            }
            data.put(String.valueOf(i+2), arr);
        }
        Set<String> keySet = data.keySet();
        int rownum = 0;
        for(String key: keySet){
            // Creating a new row in the sheet
            XSSFRow row = sheet.createRow(rownum++);
            var valueArr = data.get(key);
            int cellnum = 0;
            for(Object s : valueArr){
                XSSFCell cell = row.createCell(cellnum++);
                if(s instanceof String) {
                    cell.setCellValue((String) s);
                }
            }
        }
        // Writing the workbook
        FileOutputStream out = new FileOutputStream(fileName + ".xlsx");
        workbook.write(out);
        // Closing file output connections
        out.close();
    }

    public void exportCsv(List<T> list, String nameSheet, String fileName) throws IOException, IllegalAccessException {
        File fileCsv = new File(fileName + ".csv");
        PrintWriter csvWriter = new PrintWriter(new FileWriter(fileCsv));
        Field[] fields = ImportHistory.class.getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for(int i = 0; i < fields.length; i++){
            fieldNames[i] = fields[i].getName();
        }
        List<String> rows = new ArrayList<>();
        rows.add(String.join(",", fieldNames));
        for(int i = 0; i < list.size(); i++){
            String[] arr = new String[fields.length];
            Object obj = list.get(i);
            for(int j = 0; j < fields.length; j++){
                fields[j].setAccessible(true);
                arr[j] = String.valueOf(fields[j].get(obj));
            }
            rows.add(String.join(",", arr));
        }
        csvWriter.println(String.join("\n", rows));
        csvWriter.close();
    }
}
