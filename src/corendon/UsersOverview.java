/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author JerryJerr
 */
public class UsersOverview extends BorderPane {


    private ObservableList<UserRecord> data
            = FXCollections.observableArrayList();
    private ObservableList<UserRecord> tableData
            = FXCollections.observableArrayList();

    public void initScreen() {

        

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
        BorderPane border1 = new BorderPane();
        ScrollPane scroll2 = new ScrollPane();
        GridPane table3 = new GridPane();
        final TableView<UserRecord> tableView4 = new TableView();

        this.setTop(topBar);
        this.setCenter(border1);
        border1.setCenter(scroll2);
        scroll2.setContent(table3);
        table3.add(tableView4, 2, 0, 10, (tableData.size() + 1));

        TextField searchBar = new TextField();
        Button searchButton = new Button("Search");

        //SEARCH BAR RED TOP
        topBar.getChildren().addAll(corendonLogoView);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("white");

        TableColumn userIdCol = new TableColumn("User ID");
        TableColumn usernameCol = new TableColumn("Username");
        TableColumn passwordCol = new TableColumn("Password");
        TableColumn firstnameCol = new TableColumn("First name");
        TableColumn tussenvoegselCol = new TableColumn("Tussenvoegsel");
        TableColumn surnameCol = new TableColumn("Surname");
        TableColumn functionCol = new TableColumn("Function");

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

        tableView4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableView4.setPrefSize(800,647);
        tableView4.setItems(this.tableData);
        //data.get(1).toString();
        //data.get(3).toString();
    }

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
