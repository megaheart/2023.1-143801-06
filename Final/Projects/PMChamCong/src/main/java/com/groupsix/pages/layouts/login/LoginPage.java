package com.groupsix.pages.layouts.login;

import com.groupsix.base.IEventHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LoginPage {
    @FXML
    public TextField usernameTxtBox;
    @FXML
    public PasswordField passwordTxtBox;
    @FXML
    public Button loginBtn;

    @FXML
    public Text errorText;

    public void onLoginBtnClick(EventHandler<ActionEvent> eventHandler) {
//        this.eventHandler = eventHandler;
        loginBtn.setOnAction(eventHandler);
    }

    public String getUsername() {
        return usernameTxtBox.getText();
    }

    public String getPassword() {
        return passwordTxtBox.getText();
    }

    public void setErrorText(String errorText) {
        this.errorText.setText("");
        // Show error text after 500ms
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        // Schedule the command to run after 500 milliseconds
        executorService.schedule(() -> {
            // Your command or task to run asynchronously after 3 seconds
            this.errorText.setText(errorText);
        }, 500, TimeUnit.MILLISECONDS);

        // Shutdown the executor service after your tasks are done
        executorService.shutdown();
    }
}
