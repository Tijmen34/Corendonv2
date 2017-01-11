/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author JerryJerr
 */
public class UsersOverview extends BorderPane {

    private Stage primaryStage;
    private ObservableList<UserRecord> data
            = FXCollections.observableArrayList();
    private ObservableList<UserRecord> tableData
            = FXCollections.observableArrayList();

    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;

        

        getRecordsFromDB();
        for (int i = 0; i < this.data.size(); i++) {
            this.tableData.add(this.data.get(i));
        }

        Image corendonLogo = new Image("Corendon-Logo2.jpg");
        ImageView corendonLogoView = new ImageView();
        corendonLogoView.setImage(corendonLogo);
        corendonLogoView.setPreserveRatio(true);
        corendonLogoView.setFitHeight(800);
        corendonLogoView.setFitWidth(250);
        
        
        HBox topBar = new HBox();
        Button b = new Button();
        BorderPane border1 = new BorderPane();
        ScrollPane scroll2 = new ScrollPane();
        VBox xbox = new VBox();
        TextField text1 = new TextField();
        GridPane table3 = new GridPane();
        final TableView<UserRecord> tableView4 = new TableView();

        setTop(topBar);
        setCenter(border1);
        border1.setLeft(scroll2);
        border1.setRight(xbox);
        xbox.setPadding(new Insets(10,10,10,10));
        xbox.getChildren().addAll(text1, b);
        text1.setAlignment(Pos.CENTER);
        scroll2.setContent(table3);
        scroll2.setMinSize(800,674);
        scroll2.setMaxSize(800,674);
        table3.add(tableView4, 2, 0, 10, (tableData.size() + 1));
        


        
        // topbar voor corendon logo
        topBar.getChildren().addAll(corendonLogoView);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("white");

        //tabel colummen declareren
        TableColumn userIdCol = new TableColumn("User ID");
        TableColumn usernameCol = new TableColumn("Username");
        TableColumn passwordCol = new TableColumn("Password");
        TableColumn firstnameCol = new TableColumn("First name");
        TableColumn tussenvoegselCol = new TableColumn("Tussenvoegsel");
        TableColumn surnameCol = new TableColumn("Surname");
        TableColumn functionCol = new TableColumn("Function");

        //table vullen met de variabelen
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<>("user_id"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));
        firstnameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstname"));
        tussenvoegselCol.setCellValueFactory(
                new PropertyValueFactory<>("tussenvoegsel"));
        surnameCol.setCellValueFactory(
                new PropertyValueFactory<>("surname"));
        functionCol.setCellValueFactory(
                new PropertyValueFactory<>("function"));
        tableView4.getColumns().addAll(userIdCol, usernameCol, passwordCol, firstnameCol, tussenvoegselCol,
                surnameCol, functionCol);
        
        
            b.setOnAction((ActionEvent e) -> {
            addUser(primaryStage);
        });

        

        
        //table niet resizable
        tableView4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //lijkt me logisch dit
        tableView4.setMinSize(800,674);
        tableView4.setMaxSize(800,674);
        tableView4.setItems(this.tableData);

    }
    
    public void addUser (Stage primaryStage){
        
        
        
        
        
        
    }
    
    
    
// records van de DB halen
    public void getRecordsFromDB() {
        try (Connection conn = Sql.DbConnector();) {
            String SQL = "SELECT * FROM users";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                this.data.add(new UserRecord(rs.getString("user_id"), rs.getString("username"), 
                        rs.getString("password"), rs.getString("firstname"), 
                        rs.getString("tussenvoegsel"), rs.getString("surname"), 
                        rs.getString("function")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}
