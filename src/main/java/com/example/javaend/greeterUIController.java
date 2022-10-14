package com.example.javaend;

import com.example.javaend.Models.UserDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class greeterUIController{

    public TextField txtUsernameInput;
    public TextField txtUserPasswordInput;
    public Button btnConfirmLogin;
    public ToolBar toolbar;
    public Label lblErrorOutput;
    @FXML
    private GridPane greeterGridPane;
    //get stage

    private double x, y;

    @FXML
    protected void onButtonClose(){
        Stage st = (Stage) greeterGridPane.getScene().getWindow();
        st.close();
    }


    //for dragging window
    public void init(Stage stage){
        //stage drag
        greeterGridPane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });
        greeterGridPane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });


        //toolbar drag
        toolbar.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        toolbar.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });
    }

    public void btnLoginClicked() throws IOException {
        StageAndSceneController scController = new StageAndSceneController((Stage)greeterGridPane.getScene().getWindow());
        if(VerifyUserCredentials())
            scController.setScene("main");

    }

    private boolean VerifyUserCredentials(){
        String uName = null;
        String passwd = null;
        try {
            uName = txtUsernameInput.getText();
            passwd = txtUserPasswordInput.getText();
        }catch (Exception ex){ lblErrorOutput.setText("credentials in incorrect format");}
        UserDatabase db = new UserDatabase();
        if(!db.VerifyCredentials(uName, passwd)){
            lblErrorOutput.setText("Incorrect credentials");
            return false;
        }
        lblErrorOutput.setText("Correct");
        return true;
    }
}