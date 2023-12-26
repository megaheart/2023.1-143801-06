module com.groupsix.pmchamcong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    requires ormlite.jdbc;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires org.apache.poi.poi;

    opens com.groupsix.hrsubsystem to ormlite.jdbc;
    opens com.groupsix.attendance to ormlite.jdbc, javafx.base;
    opens com.groupsix.user to ormlite.jdbc;
    opens com.groupsix.request to ormlite.jdbc;

    opens com.groupsix.pages to javafx.fxml;
    opens com.groupsix.pages.layouts to javafx.fxml;
    opens com.groupsix.pages.layouts.login to javafx.fxml;
    opens com.groupsix.pages.layouts.departmentleader to javafx.fxml;
    opens com.groupsix.pages.layouts.employee to javafx.fxml;
    opens com.groupsix.pages.layouts.hr to javafx.fxml;
    opens com.groupsix.pages.employeehome to javafx.fxml;
    opens com.groupsix.pages.departmentleaderhome to javafx.fxml;
    opens com.groupsix.pages.hrhome to javafx.fxml;
    opens com.groupsix.pages.employeeattendance to javafx.fxml, javafx.base;
    opens com.groupsix.pages.officerdepartmentattendancereport to javafx.fxml, javafx.base;
    opens com.groupsix.pmchamcong to javafx.fxml;
    opens com.groupsix.report to javafx.fxml;
    exports com.groupsix.pmchamcong;

    opens com.groupsix.pages.officerattendancedetail to javafx.fxml;
    exports com.groupsix.pages.officerattendancedetail;

    opens com.groupsix.pages.hrchangerequest to javafx.fxml;
    exports com.groupsix.pages.hrchangerequest;

    exports com.groupsix.pages.importexcel;
    opens com.groupsix.pages.importexcel to javafx.fxml;
    exports com.groupsix.importexcel;
    opens com.groupsix.importexcel to javafx.fxml, ormlite.jdbc;

}