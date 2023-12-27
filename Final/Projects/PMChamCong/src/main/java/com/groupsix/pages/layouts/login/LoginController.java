package com.groupsix.pages.layouts.login;

import com.groupsix.user.UserService;

public class LoginController {
    public LoginController(LoginPage view){
        view.onLoginBtnClick(event -> {
            String username = view.getUsername();
            String password = view.getPassword();
            var result = UserService.getInstance().authenticate(username, password);
            if (result) {
                view.setErrorText("");
            } else {
                view.setErrorText("Sai tên đăng nhập hoặc mật khẩu");
            }
        });
    }
}
