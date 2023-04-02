module com.smol.gfm {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.smol.gfm to javafx.fxml;
    exports com.smol.gfm;
}