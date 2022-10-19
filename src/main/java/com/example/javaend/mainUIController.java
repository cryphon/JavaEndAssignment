package com.example.javaend;

import com.example.javaend.Models.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public class mainUIController {
    public Pane mainPane;
    public ToolBar toolbar;
    public TextField txtItemCodeLend;
    public TextField txtMemberIdLend;
    public Label lblUsername;
    public TableView<Item> tableViewCollection;
    public Label lblLendError;

    private User _user;
    private double x, y;
    public void init(Stage stage, User u){
        _user = u;
        lblUsername.setText(String.format("Welcome %s", _user.get_fullName()));

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


    //BEGIN methods
    public void onButtonClose(){
        Stage st = (Stage) mainPane.getScene().getWindow();
        st.close();
    }
    public void onLendClicked(){
        int itemId;
        int uid;
        //parse catch
        try{
            itemId = Integer.parseInt(txtItemCodeLend.getText());
        }catch (Exception ex) { lblLendError.setText(String.format("Incorrect item id: %s", txtItemCodeLend.getText())); return; }
        try{
            uid = Integer.parseInt(txtMemberIdLend.getText());
        }catch(Exception ex) { lblLendError.setText(String.format("Incorrect member id %s", txtMemberIdLend.getText())); return; }

        if(verifyLendInput(itemId, uid)){
            ItemDatabase idb = ItemDatabase.getInstance();
            Item item = idb.getItemById(itemId);
            UserDatabase udb = new UserDatabase();
            User user = udb.getUserById(uid);
            idb.changeItemAvailability(item, Availability.no, user);
            lblLendError.setText("Item has successfully been lent");
        }
    }

    private boolean verifyLendInput(int itemId, int uid){
        //check item exist
        ItemDatabase idb = ItemDatabase.getInstance();
        Item item = idb.getItemById(itemId);
        if(Objects.isNull(item)) {
            lblLendError.setText(String.format("item with id: %d does not exist", itemId));
            return false;
        }
        //check user exist
        UserDatabase udb = new UserDatabase();
        User u = udb.getUserById(uid);
        if(Objects.isNull(u)){
            lblLendError.setText(String.format("User with id: %d does not exist", uid));
            return false;
        }
        return true;
    }
    public void onReceiveClicked(){

    }

    public void onCollectionClicked(){
        UpdateTableView();
    }

    private void UpdateTableView(){
        tableViewCollection.getItems().clear();
        setColumnPropertyValues();
        ItemDatabase db = ItemDatabase.getInstance();
        List<Item> items = db.get_items();
        items.forEach(item -> {
            tableViewCollection.getItems().add(item);
        });
    }

    private void setColumnPropertyValues(){
        tableViewCollection.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("_id"));
        tableViewCollection.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("_availability"));
        tableViewCollection.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("_title"));
        tableViewCollection.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("_author"));

    }

    //END  methods
}
