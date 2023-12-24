package com.groupsix.pages.layouts;

import javafx.scene.Parent;
import javafx.scene.Scene;

public interface INavBar {
    void loadPage(String routerLabel, Parent scene);
    void navigateToHome();
}
