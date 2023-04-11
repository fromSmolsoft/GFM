package com.smol.gfm.controller.dialog;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class SimpleDialog {

    Dialog<String> dialog;
    ButtonType type;
    public SimpleDialog() {
        dialog = new Dialog<>();
        type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
    }


    public void show() {
        dialog.showAndWait();
    }


}
