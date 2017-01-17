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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author burak
 */
public class UserSettings extends BorderPane {
    
    String query;
    
    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection conn;
    Statement stmt;
    
    Label settings = new Label("On this page you can change your e-mail and password.");
    Label labelPwd = new Label("New password");
    Label labelMail = new Label("New e-mail");
    VBox vbox = new VBox();
    
    Button submit = new Button("Submit");
    
    TextField mail = new TextField();
    PasswordField pwd = new PasswordField();
    
    Stage primaryStage;
    
    public void initScreen(Stage primaryStage) {
        submit();
        this.setTop(settings);
        this.setAlignment(settings, Pos.CENTER);
        settings.setPadding(new Insets(25, 0, 0, 0));
        settings.setStyle("-fx-font-weight: bold;");
        this.setCenter(vbox);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(labelPwd, pwd, labelMail, mail, submit);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 50, 50, 50));
        vbox.setMaxSize(500, 300);
        vbox.setMinSize(500, 300);
        
        mail.setMaxWidth(200);
        pwd.setMaxWidth(200);
    }
    
    public void submit() {
        submit.setOnAction(e-> {
        try (Connection conn = Sql.DbConnector();) {
                
            if ((mail.getText().isEmpty() == false) && (pwd.getText().isEmpty() == false)) {
                query = "UPDATE users SET email=?,password=? WHERE username=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, mail.getText());
                pst.setString(2, pwd.getText());
                pst.setString(3, Corendon.uname);
                pst.executeUpdate();
            } else if (pwd.getText().isEmpty() == true) {
                query = "UPDATE users SET email=? WHERE username=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, mail.getText());
                pst.setString(2, Corendon.uname);
                pst.executeUpdate();
            } else if (mail.getText().isEmpty() == true) {
                query = "UPDATE users SET password=? WHERE username=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, pwd.getText());
                pst.setString(2, Corendon.uname);
                pst.executeUpdate();
            }
                System.out.println(mail.getText() + pwd.getText() + " " + query);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Information successfully updated.");
                alert.showAndWait();
                mail.clear();
                pwd.clear();
            
                } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Corendon - Luggage");
                alert.setHeaderText(null);
                alert.setContentText("There is an error in the database, please try again later.");
                alert.showAndWait();
                System.out.println("SQL ERROR");
                System.err.println(e1);
            }
        });
    }
}
