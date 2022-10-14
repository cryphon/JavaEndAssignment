package com.example.javaend;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StageAndSceneController {

    protected Scene currentScene;
    protected Stage _stage;
    public StageAndSceneController(Stage stage)
    {
        this._stage = stage;
    }

    //set scene
    public void setScene(String sceneName) throws IOException {
        //set
        if(Objects.equals(sceneName, "login"))
            this.currentScene = LoginScene(this._stage);
        if(Objects.equals(sceneName, "main"))
            this.currentScene = MainScene(this._stage);

        //display
        _stage.setScene(currentScene);
        _stage.show();
    }




    //main scene
    public Scene MainScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(program.class.getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        //scene params
        scene.getStylesheets().add(Objects.requireNonNull(program.class.getResource("mainStyle.css")).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        ((mainUIController)fxmlLoader.getController()).init(stage);
        return scene;
    }

    //greeter scene
    public Scene LoginScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(program.class.getResource("greeter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        //scene params
        scene.getStylesheets().add(Objects.requireNonNull(program.class.getResource("greeterStyle.css")).toExternalForm());
        scene.setFill(Color.TRANSPARENT);
        ((greeterUIController)fxmlLoader.getController()).init(stage);
        return scene;
    }

}
