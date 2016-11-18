/*
 * De main applicatie
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
public class Corendon extends Application {

    Connection conn;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void start(Stage primaryStage) {
        CheckConnection(); //de methode CheckConnection() wordt uitgevoerd

        GridPane mainScreen = new GridPane(); //het hoofdscherm wordt een gridpane
        Scene newscene = new Scene(mainScreen, 1200, 700, Color.rgb(0, 0, 0, 0)); //het hoofdscherm wordt hier weergegeven

        Label loginLabel = new Label("Enter your details.");
        Button login = new Button("Log in"); //maak de loginknop aan
        Button help = new Button("Help"); //maak de help knop aan

        TextField usr = new TextField(); //maak het veld aan voor username
        PasswordField pwd = new PasswordField(); //maak het pwd veld aan
        BorderPane startScreen = new BorderPane(); //maak het startscherm aan
        HBox startScreenTop = new HBox();
        HBox startScreenBottom = new HBox();
        GridPane loginScreen = new GridPane();
        Image logoCorendon = new Image("Corendon-Logo.jpg");
        Image logoLuggage = new Image("Luggage_white.png");
        Label usrLabel = new Label("Username:");
        Label pwdLabel = new Label("Password:");

        ImageView logoCorendonView = new ImageView();
        ImageView logoLuggageView = new ImageView();

        logoCorendonView.setImage(logoCorendon);
        logoLuggageView.setImage(logoLuggage);

        usrLabel.setStyle("-fx-text-fill:#D81E05");
        pwdLabel.setStyle("-fx-text-fill:#D81E05");

        usr.setPromptText("Username");
        pwd.setPromptText("Password");

        pwd.setOnKeyPressed(new EventHandler<KeyEvent>() {
            
            //deze methode zorgt ervoor dat je met de Enter knop kunt submitten/inloggen
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    try {
                        String query = "select * from users where Username=? and Password=?";
                        pst = conn.prepareStatement(query);
                        pst.setString(1, usr.getText());
                        pst.setString(2, pwd.getText());
                        rs = pst.executeQuery();

                        if (rs.next()) {
                            //label.setText("Login Successful");
                            primaryStage.setScene(newscene);
                            primaryStage.show();
                        } else {
                            loginLabel.setText("Error: Invalid username or password.");
                        }
                        usr.clear();
                        pwd.clear();
                        pst.close();
                        rs.close();
                    } catch (Exception e1) {
                        loginLabel.setText("SQL Error");
                        System.err.println(e1);
                    }
                }
            }
        });

        //de login knop backend
        login.setOnAction(e -> {
            try {
                String query = "select * from users where Username=? and Password=?";
                pst = conn.prepareStatement(query);
                pst.setString(1, usr.getText());
                pst.setString(2, pwd.getText());
                rs = pst.executeQuery();

                if (rs.next()) {
                    //label.setText("Login Successful");
                    primaryStage.setScene(newscene);
                    primaryStage.show();
                } else {
                    loginLabel.setText("Error: Invalid username or password.");
                }
                
                usr.clear();
                pwd.clear();
                pst.close();
                rs.close();
                
            } catch (Exception e1) {
                loginLabel.setText("SQL Error");
                System.err.println(e1);
            }
        });
        
        
        loginScreen.setHgap(15);
        loginScreen.setVgap(15);
        loginScreen.setPadding(new Insets(50, 30, 50, 30));

        //loginScreen.getChildren().add();
        loginScreen.add(loginLabel, 1, 2);
        loginScreen.add(usrLabel, 0, 0);
        loginScreen.add(pwdLabel, 0, 1);
        loginScreen.add(usr, 1, 0, 2, 1);
        loginScreen.add(pwd, 1, 1, 2, 1);
        loginScreen.add(login, 1, 3);
        loginScreen.add(help, 2, 3);

        startScreenTop.setAlignment(Pos.CENTER);
        startScreenTop.setStyle("-fx-background-color:#D81E05");
        startScreenTop.getChildren().add(logoLuggageView);
        startScreen.setTop(startScreenTop);
        startScreen.setCenter(loginScreen);
        startScreen.setBottom(startScreenBottom);
        startScreenBottom.setAlignment(Pos.TOP_CENTER);
        startScreenBottom.setStyle("-fx-background-color:white");
        startScreenBottom.getChildren().add(logoCorendonView);
        loginScreen.setStyle("-fx-background-color:white");
        logoCorendonView.setStyle("-fx-background-color:white");

        //startScreen.getChildren().addAll(loginScreen, logo);
        Scene scene = new Scene(startScreen, 400, 300);

        primaryStage.setTitle("Luggage - log in");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //check van tevoren de db verbinding
    public void CheckConnection() {
        conn = Sql.DbConnector();
        if (conn == null) {
            System.out.println("Connection not succesful");
            System.exit(1);
        } else {
            System.out.println("Connection Succesful");
        }

    }

}
