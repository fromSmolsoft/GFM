package com.smol.gfm.controller;

import com.smol.gfm.model.TextConst;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;


public class MyDialog {

    Dialog<String> dialog;

    public MyDialog() {
        init();
    }

    private void init() {
        dialog = new Dialog<>();
        dialog.setTitle(TextConst.ABOUT);
        dialog.setContentText(TextConst.ABOUT_DETAILS);
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
    }

    public void show() {
        dialog.showAndWait();
    }

}
