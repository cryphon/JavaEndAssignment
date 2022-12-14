package com.example.javaend;

import com.example.javaend.Models.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    public TextField txtItemCodeReceive;
    public Label lblReceiveError;
    public Label lblCollectionError;
    public TextField txtCRUDItemTitle;
    public TextField txtCRUDItemAuthor;
    public Button btnConfirmItemCRUD;
    public Button btnAddItem;
    public Button btnEditItem;
    public Button btnDelItem;

    private double x, y;
    public void init(Stage stage, User u){
        lblUsername.setText(String.format("Welcome %s", u.get_fullName()));

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
        ItemDatabase db = new ItemDatabase();
        db.update();
        Stage st = (Stage) mainPane.getScene().getWindow();
        st.close();
    }

    /*  lend/receive    */
    public void onLendClicked(){
        int itemId;
        int uid;
        //catch NaN
        try{
            itemId = Integer.parseInt(txtItemCodeLend.getText());
        }catch (Exception ex) { lblLendError.setText(String.format("Incorrect item id: %s", txtItemCodeLend.getText())); return; }
        try{
            uid = Integer.parseInt(txtMemberIdLend.getText());
        }catch(Exception ex) { lblLendError.setText(String.format("Incorrect member id %s", txtMemberIdLend.getText())); return; }

        if(verifyLendInput(itemId, uid)){
            ItemDatabase idb = new ItemDatabase();
            Item item = idb.getItemById(itemId);
            if(item.get_availability() != Availability.yes)
                return;
            UserDatabase udb = new UserDatabase();
            User user = udb.getUserById(uid);
            idb.lendItem(item, user);
            lblLendError.setText("Item has successfully been lent");
        }
    }
    private boolean verifyLendInput(int itemId, int uid){
        //check item exist
        ItemDatabase idb = new ItemDatabase();
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
        int itemId;
        //catch NaN
        try{
            itemId = Integer.parseInt(txtItemCodeReceive.getText());
        }catch (Exception ex) { lblReceiveError.setText(String.format("Incorrect item id: %s", txtItemCodeReceive.getText())); return; }
        //if valid item
        if(verifyReceiveInput(itemId)){
            ItemDatabase db = new ItemDatabase();
            Item item = db.getItemById(itemId);
            //if item available
            if(!Objects.equals(item.get_availability(), Availability.no)){
                lblReceiveError.setText(String.format("item with id: %d has not be lent out", itemId));
                return;
            }
            //lend days
            int days = (int) ChronoUnit.DAYS.between(item.get_lendingDate(), LocalDate.now());
            if(days >= 21) {
                int exDays = days - 21;
                lblReceiveError.setText(String.format("Item is late by %d days", exDays));
                return;
            }
            db.receiveItem(item);
            lblReceiveError.setText("item has successfully been returned");
        }
    }
    private boolean verifyReceiveInput(int itemId){
        ItemDatabase db = new ItemDatabase();
        Item item = db.getItemById(itemId);
        if (Objects.isNull(item)){
            lblLendError.setText(String.format("item with id: %d does not exist", itemId));
            return false;
        }
        return true;
    }

    /*  collection  */
    public void onCollectionClicked(){
        updateTableView();
        btnConfirmItemCRUD.setVisible(false);
        txtCRUDItemTitle.setVisible(false);
        txtCRUDItemAuthor.setVisible(false);
    }
    private void updateTableView(){
        tableViewCollection.getItems().clear();
        setColumnPropertyValues();
        ItemDatabase db = new ItemDatabase();
        List<Item> items = db.get_items();
        items.forEach(item -> tableViewCollection.getItems().add(item));
    }
    private void setColumnPropertyValues(){
        tableViewCollection.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("_id"));
        tableViewCollection.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("_availability"));
        tableViewCollection.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("_title"));
        tableViewCollection.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("_author"));
    }
    public void onAddItemClicked() {
        tableViewCollection.getSelectionModel().clearSelection();
        displayAddTextFields();
        btnConfirmItemCRUD.setText("Create Item");
    }
    public void onEditItemClicked(){
        Item item = getItemFromCollectionTable();
        if(Objects.isNull(item))
            return;
        //set title and auth to subfields
        txtCRUDItemTitle.setText(item.get_title());
        txtCRUDItemAuthor.setText(item.get_author());
        displayAddTextFields();
        btnConfirmItemCRUD.setText("Edit Item");
    }
    public void onDelItemClicked(){
        ///discard method if null
        Item item = getItemFromCollectionTable();
        if(Objects.isNull(item)){
            lblCollectionError.setText("no item");
            return;
        }
        ItemDatabase db = new ItemDatabase();
        db.deleteItem(item);
        updateTableView();
    }
    public void onCRUDItemClicked(){
        ItemDatabase db = new ItemDatabase();
        String title = txtCRUDItemTitle.getText();
        String author = txtCRUDItemAuthor.getText();
        if(Objects.equals(btnConfirmItemCRUD.getText(), "Create Item")){
            db.addNewItem(title, author);
        }
        if(Objects.equals(btnConfirmItemCRUD.getText(), "Edit Item")){
            //discard method if null
            Item item = getItemFromCollectionTable();
            if(Objects.isNull(item))
                return;
            db.editItemTitleAndAuthor(item, txtCRUDItemTitle.getText(), txtCRUDItemAuthor.getText());
        }
        displayHideTextFields();
        updateTableView();
    }
    private Item getItemFromCollectionTable(){
        Item item = null;
        try {
            item = tableViewCollection.getSelectionModel().getSelectedItem();
        }catch (Exception ex) { lblCollectionError.setText("error selecting item"); }
        //display error but return null
        if(Objects.isNull(item))
            lblCollectionError.setText("no row selected");
        return item;
    }
    private void displayAddTextFields(){
        txtCRUDItemTitle.setVisible(true);
        txtCRUDItemAuthor.setVisible(true);
        btnConfirmItemCRUD.setVisible(true);
    }
    private void displayHideTextFields(){
        txtCRUDItemTitle.setVisible(false);
        txtCRUDItemAuthor.setVisible(false);
        btnConfirmItemCRUD.setVisible(false);
    }

    /* members*/
    public void onAddMemberClicked(){}
    public void onEditMemberClicked(){}
    public void onDelMemberClicked(){}

    //END  methods
}
