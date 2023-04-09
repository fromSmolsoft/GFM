package com.smol.gfm;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
public class Main extends Application {
    public static void main(String[] args) {
        log.info("Main.main");
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {
        log.info("start()");
        FXMLLoader  fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));

        Rectangle2D screen     = Screen.getPrimary().getBounds();
        double      width      = screen.getWidth() * 0.8d;
        double      height     = screen.getHeight() * 0.8d;

        Scene       scene      = new Scene(fxmlLoader.load(), width, height);
        scene.getStylesheets().add("dark_theme.css");
        stage.setTitle("GFM");
        stage.setScene(scene);
        stage.show();
    }
}