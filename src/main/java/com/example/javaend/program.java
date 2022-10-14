package com.example.javaend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class program extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //stage params
        stage.setHeight(420);
        stage.setWidth(640);
        stage.initStyle(StageStyle.TRANSPARENT);

        StageAndSceneController scController = new StageAndSceneController(stage);
        scController.setScene("login");
    }

    public static void main(String[] args) {
        launch();
    }

}

