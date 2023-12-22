package com.groupsix.user;

import com.groupsix.base.EventHub;
import com.groupsix.base.IEventHandler;

public class UserService {
	private static UserService instance = new UserService();
	public static UserService getInstance() {
		return instance;
	}

	private User currentUser = null;

	private UserService() {
	}

	public User getCurrentUser() {
		return currentUser;
	}

	public boolean authenticate(String username, String password) {
		var user = UserFactory.getInstance().createRepository().getUserByUsername(username);
		if(user != null && user.getPassword().equals(password)) {
			currentUser = user;
			onUserChangedHandles.publish(this, user);
			return true;
		}
		return false;
	}

	public void logout() {
		if(currentUser != null) {
			currentUser = null;
			onUserChangedHandles.publish(this, null);
		}
	}

	private final EventHub<UserService, User> onUserChangedHandles = new EventHub<>();

	public void onUserChanged(IEventHandler<UserService, User> handler) {
		onUserChangedHandles.subscribe(handler);
	}

	public void unsubscribeOnUserChanged(IEventHandler<UserService, User> handler) {
		onUserChangedHandles.unsubscribe(handler);
	}
}
