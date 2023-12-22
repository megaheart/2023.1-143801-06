package com.groupsix.user;

import java.util.ArrayList;

public interface IUserRepository {

	ArrayList<User> getUsers();

	User getUserByUsername(String username);

}
