package com.groupsix.importexcel;

public class ImportHistoryFactory {
    private Class<IHistoryImportRepository> repoClass;

    private static ImportHistoryFactory instance = new ImportHistoryFactory();

    private ImportHistoryFactory() {
    }

    public IHistoryImportRepository createRepository() {
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

    public static ImportHistoryFactory getInstance() {
        return instance;
    }
}
