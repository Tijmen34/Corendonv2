/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Application;
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
        int aantalStickies = 8;
        int buttonIterator = 0;

        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();
        GridPane tableSticky2 = new GridPane();
        ScrollPane scroll2 = new ScrollPane();
        GridPane table3 = new GridPane();
        TableView tableView4 = new TableView();
        TableView tableViewSticky3 = new TableView();

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
        tableSticky2.add(tableViewSticky3, 2, 0, aantalStickies + 1, 10);
        scroll2.setContent(table3);
        table3.add(tableView4, 2, 0, aantalRecords + 1, 10);
        
        Separator line = new Separator();
        line.setOrientation(Orientation.VERTICAL);
        table3.add(line, 0, 0, aantalRecords + 1, 10);
        
        
                
        //-------------------------------------------

        //buttons, eerst de buttons om records te verplaatsen naar sticky tabel,
        // en daarna de "unsticky" butons.
        StackPane cell[] = new StackPane[aantalRecords + 1];
        StackPane cellS[] = new StackPane[aantalStickies + 1];
        Button[] sticky = new Button[aantalRecords];
        Button[] unSticky = new Button[aantalStickies];
        while (buttonIterator < aantalRecords) {
            cell[buttonIterator] = new StackPane();
            cell[buttonIterator].setMaxSize(30.0, 30.0);
            cell[buttonIterator].setMinSize(30.0, 30.0);
            
            sticky[buttonIterator] = new Button("^");
            sticky[buttonIterator].setPrefSize(25, 25);
            table3.add(cell[buttonIterator], 1, buttonIterator + 1);
            cell[buttonIterator].getChildren().add(sticky[buttonIterator]);
            buttonIterator++;
            
        }
        cell[buttonIterator] = new StackPane();
        cell[buttonIterator].setMaxSize(30.0, 30.0);
        cell[buttonIterator].setMinSize(30.0, 30.0);
        table3.add(cell[buttonIterator], 1, 0);
        
        buttonIterator = 0;
        while (buttonIterator < aantalStickies) {
            cellS[buttonIterator] = new StackPane();
            cellS[buttonIterator].setMaxSize(30.0, 30.0);
            cellS[buttonIterator].setMinSize(30.0, 30.0);
            
            unSticky[buttonIterator] = new Button("v");
            unSticky[buttonIterator].setPrefSize(25, 25);
            tableSticky2.add(cellS[buttonIterator], 1, buttonIterator + 1);
            cellS[buttonIterator].getChildren().add(unSticky[buttonIterator]);
            buttonIterator++;
        }
        cellS[buttonIterator] = new StackPane();
        cellS[buttonIterator].setMaxSize(30.0, 30.0);
        cellS[buttonIterator].setMinSize(30.0, 30.0);
        tableSticky2.add(cellS[buttonIterator], 1, 0);
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

        tableViewSticky3.getColumns().addAll(lostIdColSt, labelNrColSt, flightNrColSt,
                typeColSt, brandColSt, primaryColorColSt, secondaryColorColSt, infoColSt,
                customerIdColSt);
        tableViewSticky3.setPrefSize(800, 100);
        //--------------------------------------------

    }
}
