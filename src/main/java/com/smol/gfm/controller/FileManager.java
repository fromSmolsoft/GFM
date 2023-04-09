package com.smol.gfm.controller;

import javafx.event.Event;
import javafx.scene.Node;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {

    private final FileChooser fileChooser;

    public FileManager() {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(Paths.get(System.getProperty("user.home"), "Documents")
                .toFile());
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
    }

    public Path openFile(Event event) throws Exception {
        Window window = ((Node) event.getTarget()).getScene().getWindow();
        File   file   = fileChooser.showOpenDialog(window);
        if (file != null) {
            if (file.getName().endsWith(".xml")) {
                return Path.of(file.getPath());
            } else {
                throw new Exception(file.getName() + " has invalid file-extension.");
            }
        } else return null;
    }

    public Path saveFile(Event event) {
        Window window = ((Node) event.getTarget()).getScene().getWindow();
        fileChooser.setInitialFileName(".xml");
        File file = fileChooser.showSaveDialog(window);
        if (file != null) {
            return Path.of(file.getPath());
        } else return null;
    }
}
