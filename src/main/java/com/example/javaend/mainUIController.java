package com.example.javaend;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class mainUIController {
    public javafx.scene.layout.Pane mainPane;

    public void init(Stage stage){

    }


    @FXML
    protected void onButtonClose(){
        Stage st = (Stage) mainPane.getScene().getWindow();
        st.close();
    }
}
