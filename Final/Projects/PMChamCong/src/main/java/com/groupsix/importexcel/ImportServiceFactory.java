package com.groupsix.importexcel;

public class ImportServiceFactory {
    private static ImportServiceFactory instance = new ImportServiceFactory();

    private Class<IImportService> serviceClass;

    private ImportServiceFactory() {
    }

    public IImportService createService() {
        try {
            return serviceClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void registerRepository(Class serviceClass) {
        this.serviceClass = serviceClass;
    }

    public static ImportServiceFactory getInstance() {
        return instance;
    }
}
