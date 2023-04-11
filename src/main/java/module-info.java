module com.smol.gfm {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.xml;
    requires java.logging;


    exports com.smol.gfm.controller;
    exports com.smol.gfm.exception;
    exports com.smol.gfm.model;
    exports com.smol.gfm.service;
    exports com.smol.gfm;

    opens com.smol.gfm to javafx.fxml;
    opens com.smol.gfm.controller to javafx.fxml;
    opens com.smol.gfm.model to javafx.fxml;
    opens com.smol.gfm.service to javafx.fxml;
    exports com.smol.gfm.controller.dialog;
    opens com.smol.gfm.controller.dialog to javafx.fxml;
}