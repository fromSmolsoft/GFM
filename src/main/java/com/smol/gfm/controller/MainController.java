package com.smol.gfm.controller;

import com.smol.gfm.controller.dialog.AboutDialog;
import com.smol.gfm.controller.dialog.ErrorDialog;
import com.smol.gfm.exception.InvalidFileExtension;
import com.smol.gfm.model.DocumentObj;
import com.smol.gfm.service.XmlProcessor;
import com.smol.gfm.service.XmlReader;
import com.smol.gfm.service.XmlWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import lombok.SneakyThrows;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class MainController {

    @FXML
    private Label             labelOldDocument;
    @FXML
    private Label             labelNewDocument;
    @FXML
    private ProgressIndicator progressIndicator;

    private FileManager fileManager;
    private DocumentObj oldDocument;
    private DocumentObj newDocument;


    @FXML
    protected void onLoadButtonClick(ActionEvent event) {
        progressIndicator.setVisible(true);
        fileManager = new FileManager();
        Path file = null;
        try {
            file = fileManager.openFile(event);
        } catch (InvalidFileExtension e) {
            ErrorDialog errorDialog = new ErrorDialog(e.getMessage());
            errorDialog.show();
        }
        if (file != null) {
            XmlReader reader = new XmlReader(file);
            try {
                oldDocument = reader.read();
            } catch (ParserConfigurationException | IOException | SAXException e) {
                ErrorDialog errorDialog = new ErrorDialog(e.toString());
                errorDialog.show();
            }
            labelOldDocument.setText(oldDocument.toString());
        }
        progressIndicator.setVisible(false);
    }

    @FXML
    protected void onProcessBtnCLick() {
        progressIndicator.setVisible(true);
        XmlProcessor processor = new XmlProcessor();
        newDocument = processor.processDoc(oldDocument);
        labelNewDocument.setText(newDocument.toString());
        progressIndicator.setVisible(false);
    }

    @SneakyThrows
    public void onExportBtnCLick(ActionEvent event) {
        progressIndicator.setVisible(true);
        fileManager = new FileManager();
        Path export = fileManager.saveFile(event);
        if (export != null) {
            XmlWriter writer = new XmlWriter(newDocument, export);
            writer.completeDocument();
        }
        progressIndicator.setVisible(false);
    }

    public void onAboutBtnCLick() {
        AboutDialog aboutDialog = new AboutDialog();
        aboutDialog.show();
    }
}

//todo create dedicated viewmodel instead of object.toString()