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
public class LuggageOverview extends BorderPane{
    
    
    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
    public void initScreen() {
        int aantalRecords = 10;
        int aantalStickies = 2;
        int buttonIterator = 0;
        
        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();
        GridPane tableSticky2 = new GridPane();
        ScrollPane  scroll2 = new ScrollPane();
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
        tableSticky2.add(tableViewSticky3, 2, 0, aantalStickies, 10);
        scroll2.setContent(table3);
        table3.add(tableView4, 2, 0, aantalRecords, 10);
        //-------------------------------------------
        
        //buttons
        
        Button[] sticky = new Button[aantalRecords];
        Button[] unSticky = new Button[aantalStickies];
        while (buttonIterator < aantalRecords) {
            sticky[buttonIterator] = new Button("^");
            sticky[buttonIterator].setPrefSize(20, 20);
            table3.add(sticky[buttonIterator], 1, buttonIterator);
            buttonIterator++;
        }
        buttonIterator = 0;
        while (buttonIterator < aantalStickies) {
            unSticky[buttonIterator] = new Button("v");
            unSticky[buttonIterator].setPrefSize(20, 20);
            tableSticky2.add(unSticky[buttonIterator], 1, buttonIterator);
            buttonIterator++;
        }
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
        
        TableColumn firstNameCol = new TableColumn("First Name");
        TableColumn surNameCol = new TableColumn("Last Name");
        TableColumn townCol = new TableColumn("Town/place");
        
        tableView4.getColumns().addAll(firstNameCol, surNameCol, townCol);
        tableView4.setPrefSize(800, 500);
        
        
        //-------------------------------------------
        
        //Sticky Tabel
        
        TableColumn firstNameColSt = new TableColumn("First Name");
        TableColumn surNameColSt = new TableColumn("Last Name");
        TableColumn townColSt = new TableColumn("Town/place");
        
        tableViewSticky3.getColumns().addAll(firstNameColSt, surNameColSt, townColSt);
        tableViewSticky3.setPrefSize(800, 100);
        //--------------------------------------------
        
        
        
        
        
    }
}


