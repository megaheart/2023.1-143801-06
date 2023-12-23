package com.groupsix.importexcel;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "HistoryImport")
public class ImportHistory {

    @DatabaseField(generatedId = true)
    private String id;

    @DatabaseField()
    private String time;

    @DatabaseField()
    private String createdBy;

    public ImportHistory() {
    }

    public ImportHistory(String id, String time, String createdBy) {
        this.id = id;
        this.time = time;
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
