package com.groupsix.user;

import java.util.ArrayList;

public class FakeUserRepository implements IUserRepository{
    private ArrayList<User> users = new ArrayList<>();
    public FakeUserRepository() {
        var user = new User() {{
            setUsername("user");
            setPassword("1234");
            setRole("user");
        }};
        users.add(user);
    }
    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public User getUserByUsername(String username) {
        for (var user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
