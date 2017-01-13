/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author Burak
 */
public class Statistics extends BorderPane {
    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection conn;
    Statement stmt;
    private Stage primaryStage;
    
        public void initScreen(Stage primaryStage) {
            this.primaryStage = primaryStage;
            CheckConnection();
            
            
        }
        
        //check van tevoren de db verbinding
    public void CheckConnection() {
        conn = Sql.DbConnector();
        if (conn == null) {
            System.out.println("Connection lost.");
            System.exit(1);
        }
    }
}
