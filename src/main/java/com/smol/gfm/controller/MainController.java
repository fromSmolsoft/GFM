package com.smol.gfm.controller;

import com.smol.gfm.model.DocumentObj;
import com.smol.gfm.model.TextConst;
import com.smol.gfm.service.XmlProcessor;
import com.smol.gfm.service.XmlReader;
import com.smol.gfm.service.XmlWriter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import lombok.SneakyThrows;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;

public class MainController {
    FileManager fileManager;
    @FXML
    private Button            btnLoad;
    @FXML
    private Button            btnExport;
    @FXML
    private Label             labelOldDocument;
    @FXML
    private Label             labelNewDocument;
    @FXML
    private Button            btnAbout;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button            btnRun;
    private DocumentObj       oldDocument;
    private DocumentObj       newDocument;
    private Path              path;

    @FXML
    protected void onLoadButtonClick(ActionEvent event) {
        oldDocument = readDocument(event);
        labelOldDocument.setText(oldDocument.toString());
    }

    @FXML
    protected void onProccesBtnCLick() {
        XmlProcessor processor = new XmlProcessor();
        newDocument = processor.processDoc(oldDocument);
        labelNewDocument.setText(newDocument.toString());
    }

    /** Temporary method */
    @SneakyThrows
    private DocumentObj readDocument(Event event) {
        fileManager = new FileManager();
        XmlReader   reader      = new XmlReader(fileManager.openFile(event));
        DocumentObj documentObj = null;
        try {
            documentObj = reader.read();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
            documentObj = new DocumentObj();
            documentObj.setTitle(TextConst.ERROR);
        }
        return documentObj;
    }

    public void onAboutBtnCLick(ActionEvent actionEvent) {
        MyDialog myDialog = new MyDialog();
        myDialog.show();
    }

    @SneakyThrows
    public void onExportBtnCLick(ActionEvent actionEvent) {
        fileManager = new FileManager();
        XmlWriter writer = new XmlWriter(newDocument, fileManager.saveFile(actionEvent));
        writer.completeDocument();
    }

}

//todo create dedicated viewmodel instead of object.toString()