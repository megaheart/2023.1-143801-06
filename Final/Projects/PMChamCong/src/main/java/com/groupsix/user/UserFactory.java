package com.groupsix.user;

public class UserFactory {

	private Class<IUserRepository> repoClass;

	private static UserFactory instance = new UserFactory();

	private UserFactory() {
	}

	public IUserRepository createRepository() {
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

	public static UserFactory getInstance() {
		return instance;
	}

}
