package com.smol.gfm.controller.dialog;

import com.smol.gfm.model.TextConst;

public class ErrorDialog extends SimpleDialog {

    public ErrorDialog(String errorMessage) {
        dialog.setTitle(TextConst.ERROR);
        dialog.setContentText(errorMessage);
    }

}
