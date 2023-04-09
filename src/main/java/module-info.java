module com.smol.gfm {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires java.xml;
    requires java.logging;


    opens com.smol.gfm to javafx.fxml;
    exports com.smol.gfm;
    exports com.smol.gfm.model;
    opens com.smol.gfm.model to javafx.fxml;
    exports com.smol.gfm.service;
    opens com.smol.gfm.service to javafx.fxml;
    exports com.smol.gfm.controller;
    opens com.smol.gfm.controller to javafx.fxml;
}