/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
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
 * @author Jeroen de Jong
 */
public class LuggageOverview extends BorderPane {

    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
    public void initScreen() {
        int aantalRecords = 20;
        int aantalStickies = 4;
        int buttonIterator = 0;
        int recordIterator = 0;

        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();
        GridPane tableSticky2 = new GridPane();
        ScrollPane scroll2 = new ScrollPane();
        GridPane table3 = new GridPane();
        //TableView tableView4 = new TableView();
        TableView tableViewSticky3 = new TableView();
        final TableView<LuggageRecord2> tableView4 = new TableView();

        /*
        hierarchie:
                      LuggageOverview
        border1,                        topBar
        tableSticky2,scroll2
                      table3
        
         */
        this.setTop(topBar);
        this.setCenter(border1);
        border1.setTop(tableSticky2);
        border1.setCenter(scroll2);
        tableSticky2.add(tableViewSticky3, 2, 0, 10, aantalStickies + 1);
        scroll2.setContent(table3);
        table3.add(tableView4, 2, 0, 10, (aantalRecords + 1));

        //-------------------------------------------
        //-------------------------------------------
        //Rode balk bovenin het scherm
        TextField searchBar = new TextField();
        Button searchButton = new Button();

        topBar.getChildren().addAll(searchBar, searchButton);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color:#D81E05");
        // ------------------------------------------

        //Tabel
        TableColumn lostIdCol = new TableColumn("Lost ID");
        TableColumn labelNrCol = new TableColumn("Label nr");
        TableColumn flightNrCol = new TableColumn("Flight nr");
        TableColumn typeCol = new TableColumn("Type");
        TableColumn brandCol = new TableColumn("Brand Name");
        TableColumn primaryColorCol = new TableColumn("Color 1");
        TableColumn secondaryColorCol = new TableColumn("Color 2");
        TableColumn infoCol = new TableColumn("Add. info");
        TableColumn customerIdCol = new TableColumn("Customer ID");

        lostIdCol.setCellValueFactory(
                new PropertyValueFactory<>("lostId"));
        labelNrCol.setCellValueFactory(
                new PropertyValueFactory<>("labelNr"));
        flightNrCol.setCellValueFactory(
                new PropertyValueFactory<>("flightNr"));
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brandName"));
        primaryColorCol.setCellValueFactory(
                new PropertyValueFactory<>("primaryColor"));
        secondaryColorCol.setCellValueFactory(
                new PropertyValueFactory<>("secondaryColor"));
        infoCol.setCellValueFactory(
                new PropertyValueFactory<>("info"));
        customerIdCol.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));

        tableView4.getColumns().addAll(lostIdCol, labelNrCol, flightNrCol,
                typeCol, brandCol, primaryColorCol, secondaryColorCol, infoCol,
                customerIdCol);
        tableView4.setPrefSize(800, (aantalRecords * 30) + 30);

        //-------------------------------------------
        //Sticky Tabel
        TableColumn lostIdColSt = new TableColumn("Lost ID");
        TableColumn labelNrColSt = new TableColumn("Label nr");
        TableColumn flightNrColSt = new TableColumn("Flight nr");
        TableColumn typeColSt = new TableColumn("Type");
        TableColumn brandColSt = new TableColumn("Brand Name");
        TableColumn primaryColorColSt = new TableColumn("Color 1");
        TableColumn secondaryColorColSt = new TableColumn("Color 2");
        TableColumn infoColSt = new TableColumn("Add. info");
        TableColumn customerIdColSt = new TableColumn("Customer ID");

        lostIdColSt.setCellValueFactory(
                new PropertyValueFactory<>("lostId"));
        labelNrColSt.setCellValueFactory(
                new PropertyValueFactory<>("labelNr"));
        flightNrColSt.setCellValueFactory(
                new PropertyValueFactory<>("flightNr"));
        typeColSt.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        brandColSt.setCellValueFactory(
                new PropertyValueFactory<>("brandName"));
        primaryColorColSt.setCellValueFactory(
                new PropertyValueFactory<>("primaryColor"));
        secondaryColorColSt.setCellValueFactory(
                new PropertyValueFactory<>("secondaryColor"));
        infoColSt.setCellValueFactory(
                new PropertyValueFactory<>("info"));
        customerIdColSt.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));

        tableViewSticky3.getColumns().addAll(lostIdColSt, labelNrColSt, flightNrColSt,
                typeColSt, brandColSt, primaryColorColSt, secondaryColorColSt, infoColSt,
                customerIdColSt);
        tableViewSticky3.setPrefSize(800, 100);
        //--------------------------------------------

        //test record
        LuggageRecord2 testRecord = new LuggageRecord2("0001", "3R5F2", "MH370",
                "Suitcase", "jemoeder", "Red", "Black",
                "NULL", "12324", "Missing", false);
        //-------------------------------------------
        //tabel vullen
        
        final ObservableList<LuggageRecord2> data
                = FXCollections.observableArrayList();
        
                        new LuggageRecord2("0001", "3R5F2", "MH370",
                                "Suitcase", "jemoeder", "Red", "Black",
                                "NULL", "12324", "Missing", false);
                //                    new Person("Jacob", "Smith", "jacob.smith@example.com"),
                //                    new Person("Isabella", "Johnson", "isabella.johnson@example.com"),
                //                    new Person("Ethan", "Williams", "ethan.williams@example.com"),
                //                    new Person("Emma", "Jones", "emma.jones@example.com"),
                //                    new Person("Michael", "Brown", "michael.brown@example.com")
                //);
                
        tableView4.setItems(data);
        //--------------------------------------------

        //buttons, eerst de buttons om records te verplaatsen naar sticky tabel,
        // en daarna de "unsticky" butons.
        //StackPanes zijn er om de rijen in het grid de juiste grootte en plek te geven.
        StackPane cell[] = new StackPane[tableView4.getItems().size() + 1];    //Maak 1 extra voor lege plek naar header van tabel.
        StackPane cellS[] = new StackPane[aantalStickies + 1];  //Maak 1 extra voor lege plek naar header van tabel.
        Button[] sticky = new Button[tableView4.getItems().size()];
        Button[] unSticky = new Button[aantalStickies];

        while (buttonIterator < tableView4.getItems().size()) {
            cell[buttonIterator] = new StackPane();
            cell[buttonIterator].setMaxSize(30.0, 24.0);
            cell[buttonIterator].setMinSize(30.0, 24.0);

            sticky[buttonIterator] = new Button("^");
            sticky[buttonIterator].setPrefSize(24, 24);
            table3.add(cell[buttonIterator], 1, buttonIterator + 1);
            cell[buttonIterator].getChildren().add(sticky[buttonIterator]);

            buttonIterator++;

        }
        cell[buttonIterator] = new StackPane();         //laatste StackPane moet bovenin zonder button.
        cell[buttonIterator].setMaxSize(30.0, 24.0);
        cell[buttonIterator].setMinSize(30.0, 24.0);
        table3.add(cell[buttonIterator], 1, 0);

        buttonIterator = 0;
        while (buttonIterator < aantalStickies) {
            cellS[buttonIterator] = new StackPane();
            cellS[buttonIterator].setMaxSize(30.0, 24.0);
            cellS[buttonIterator].setMinSize(30.0, 24.0);

            unSticky[buttonIterator] = new Button("v");
            unSticky[buttonIterator].setPrefSize(24, 24);
            tableSticky2.add(cellS[buttonIterator], 1, buttonIterator + 1);
            cellS[buttonIterator].getChildren().add(unSticky[buttonIterator]);
            buttonIterator++;
        }
        cellS[buttonIterator] = new StackPane();        //laatste StackPane moet bovenin zonder button.
        cellS[buttonIterator].setMaxSize(30.0, 24.0);
        cellS[buttonIterator].setMinSize(30.0, 24.0);
        tableSticky2.add(cellS[buttonIterator], 1, 0);
        //-------------------------------------------

        //Buttons functioneel
//        buttonIterator = 0;
//        while (buttonIterator < tableView4.getItems().size()) {
//            final int tmpIterator = buttonIterator;
//            sticky[buttonIterator].setOnAction((ActionEvent e) -> {
//                
//                moveToSticky(tmpIterator, data);
//            });
//        }
//    }
//    
//    
//    public static ObservableList moveToSticky(int buttonIterator) {
//        data.remove(buttonIterator);
//        
    }
}
