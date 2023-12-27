package com.groupsix.request;

import com.groupsix.attendance.AttendanceFactory;
import com.groupsix.attendance.IOfficerAttendanceRepository;
import javafx.application.Application;
import javafx.stage.Stage;

public class RequestFactory {

    private Class<IRequestRepository> repoClass = null;

    private static RequestFactory instance = new RequestFactory();

    private RequestFactory() {
    }

    public IRequestRepository createRepository() {
        try {
            return repoClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerRepository(Class repoClass) {
        this.repoClass = repoClass;
    }

    public static RequestFactory getInstance() {
        return instance;
    }
}
