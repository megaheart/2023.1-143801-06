package com.groupsix.pages.importexcel;

public class ImportLogHistory {
    private String id;
    private String time;

    private String createdBy;

    public ImportLogHistory(String id, String time, String createdBy) {
        this.id = id;
        this.time = time;
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
