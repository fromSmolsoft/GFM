package com.smol.gfm.controller.dialog;

import com.smol.gfm.model.TextConst;


public class AboutDialog  extends SimpleDialog {
    public AboutDialog() {
    dialog.setTitle(TextConst.ABOUT);
    dialog.setContentText(TextConst.ABOUT_DETAILS);
    }
}
