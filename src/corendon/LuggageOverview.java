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
 * @author JerryJerr
 */
public class LuggageOverview extends BorderPane{
    
    
    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
    public void initScreen() {
        
        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();
        GridPane tableSticky2 = new GridPane();
        ScrollPane  scroll2 = new ScrollPane();
        GridPane table3 = new GridPane();
        
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
        scroll2.setContent(table3);
        
        TextField searchBar = new TextField();
        Button searchButton = new Button();
        
        topBar.getChildren().addAll(searchBar, searchButton);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color:#D81E05");
        
        
        
        
    }
}


