package com.groupsix.pages.hrchangerequest;

import com.groupsix.pages.FXRouter;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RequestDetailController implements Initializable {
    private RequestDetailView viewDetail;

    public RequestDetailController(RequestDetailView viewDetail) {
        this.viewDetail = viewDetail;
        initialize(null, null);
    }

    public void initialize(URL location, ResourceBundle resources){
        // Close Button
        this.viewDetail.closeBtn.setOnAction(event -> {
            var ctrl = (RequestHomeController) FXRouter.goTo("requestdetail");
            ctrl.initialize(null, null);
        });

        // Reject Button

    }
}
