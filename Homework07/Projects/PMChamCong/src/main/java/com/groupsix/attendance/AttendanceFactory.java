package com.groupsix.attendance;

import java.util.HashMap;

public class AttendanceFactory {
    private static AttendanceFactory attendance = new AttendanceFactory();
    public static AttendanceFactory getInstance() {
        return attendance;
    }
    private Class<IOfficerAttendanceRepository> repoClass;
    public void registerRepository(Class<IOfficerAttendanceRepository> repoClass) {
        this.repoClass = repoClass;
    }
    public IOfficerAttendanceRepository createRepository(){
        try {
            return repoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
