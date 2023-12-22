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
    opens com.groupsix.attendance to ormlite.jdbc;
    opens com.groupsix.user to ormlite.jdbc;

    opens com.groupsix.pmchamcong to javafx.fxml;
    exports com.groupsix.pmchamcong;

    opens com.groupsix.pages.officerattendancedetail to javafx.fxml;
    exports com.groupsix.pages.officerattendancedetail;

    exports com.groupsix.pages.importexcel;
    opens com.groupsix.pages.importexcel to javafx.fxml;

}