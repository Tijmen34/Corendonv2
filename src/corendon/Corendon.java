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
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
public class Corendon extends Application {

    Connection conn;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @Override
    public void start(Stage primaryStage) {
        CheckConnection(); //de methode CheckConnection() wordt uitgevoerd

        /*
        MissingForm.java
         */
        TabPane tabScreen = new TabPane(); //het hoofdscherm
        Tab missing = new Tab();
        MissingForm missingContent = new MissingForm(); //ipv gridpane maken we een instantie onze eigen versie van gridpane.
        missingContent.initScreen(); //hier roepen we de methode aan die alle elementen van het formulier toevoegd.
        Scene newscene = new Scene(tabScreen, 500, 500, Color.rgb(0, 0, 0, 0)); //het hoofdscherm wordt hier weergegeven
        /*
         */

        Label loginLabel = new Label("Enter your details.");

        //de knoppen op het login scherm
        Button login = new Button("Log in");
        Button help = new Button("Help");

        //de fields voor username en password
        TextField usrField = new TextField();
        PasswordField pwdField = new PasswordField();

        //de default tekst wat in de fields staat
        usrField.setPromptText("Username");
        pwdField.setPromptText("Password");

        BorderPane startScreen = new BorderPane(); //maak het startscherm aan
        HBox startScreenTop = new HBox(); //"scherm" voor de luggage logo
        HBox startScreenBottom = new HBox(); //"scherm" voor de corendon logo
        GridPane loginScreen = new GridPane(); //het gedeelte voor het loginscherm

        //importeer de logo's
        Image logoCorendon = new Image("Corendon-Logo.jpg");
        Image logoLuggage = new Image("Luggage_white.png");

        //tekst naast de fields
        Label usrLabel = new Label("Username:");
        Label pwdLabel = new Label("Password:");

        //geef de tekst een kleur
        usrLabel.setStyle("-fx-text-fill:#D81E05");
        pwdLabel.setStyle("-fx-text-fill:#D81E05");

        //laat logo zien
        ImageView logoCorendonView = new ImageView();
        ImageView logoLuggageView = new ImageView();

        //geef logo
        logoCorendonView.setImage(logoCorendon);
        logoLuggageView.setImage(logoLuggage);

        pwdField.setOnKeyPressed(new EventHandler<KeyEvent>() {

            //deze methode zorgt ervoor dat je met de Enter knop kunt submitten/inloggen
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ENTER) {
                    try {
                        String query = "select * from users where Username=? and Password=?";
                        pst = conn.prepareStatement(query);
                        pst.setString(1, usrField.getText());
                        pst.setString(2, pwdField.getText());
                        rs = pst.executeQuery();

                        if (rs.next()) {
                            primaryStage.setScene(newscene);
                            primaryStage.show();
                        } else {
                            loginLabel.setText("Invalid username/password.");
                        }
                        usrField.clear();
                        pwdField.clear();
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
                pst.setString(1, usrField.getText());
                pst.setString(2, pwdField.getText());
                rs = pst.executeQuery();

                if (rs.next()) {
                    primaryStage.setScene(newscene);
                    primaryStage.show();
                } else {
                    loginLabel.setText("Invalid username/password.");
                }

                usrField.clear();
                pwdField.clear();
                pst.close();
                rs.close();

            } catch (Exception e1) {
                loginLabel.setText("SQL Error");
                System.err.println(e1);
            }
        });

        /*
        Jeroen wats dit kil
         */
        loginScreen.setHgap(15);
        loginScreen.setVgap(15);
        loginScreen.setPadding(new Insets(50, 30, 50, 30));
        /*
         */

        /*
        Het login schermpje
         */
        loginScreen.add(loginLabel, 1, 2, 3, 1);
        loginScreen.add(usrLabel, 0, 0);
        loginScreen.add(pwdLabel, 0, 1);
        loginScreen.add(usrField, 1, 0, 2, 1);
        loginScreen.add(pwdField, 1, 1, 2, 1);
        loginScreen.add(login, 1, 3);
        loginScreen.add(help, 2, 3);
        /*
         */

        /* 
        Luggage logo
         */
        startScreenTop.setAlignment(Pos.CENTER);
        startScreenTop.setStyle("-fx-background-color:#D81E05");
        startScreenTop.getChildren().add(logoLuggageView);
        startScreen.setTop(startScreenTop);
        /*
         */

        startScreen.setCenter(loginScreen); //zet loginScreen in het midden
        startScreen.setBottom(startScreenBottom); //zet luggage logo onderin

        /*
        Corendon logo
         */
        startScreenBottom.setAlignment(Pos.TOP_CENTER);
        startScreenBottom.setStyle("-fx-background-color:white");
        startScreenBottom.getChildren().add(logoCorendonView);
        /*
         */

        loginScreen.setStyle("-fx-background-color:white"); //achtergrond wit
        logoCorendonView.setStyle("-fx-background-color:white"); //idem

        /*
        Het gehele loginscherm
         */
        Scene scene = new Scene(startScreen, 350, 350);
        primaryStage.setTitle("Luggage - log in");
        primaryStage.setScene(scene);
        primaryStage.show();
        /*
         */

        /*
        De tabs van het hoofdscherm
         */
        missing.setText("");
        tabScreen.getTabs().add(missing);
        missing.setContent(missingContent);
        missing.setStyle("-fx-background-color:#D81E05");
        missingContent.setStyle("-fx-background-color:white");
        tabScreen.setSide(Side.LEFT);
        missing.setGraphic(new Label("Missing"));
        missing.getGraphic().setStyle("-fx-text-fill:white");
        tabScreen.setStyle("-fx-tab-min-width:20px;-fx-tab-max-width:20px;-fx-tab-min-height:80px; -fx-tab-max-height:80px");
        missing.setClosable(false);
        /*
         */
    }

    //check van tevoren de db verbinding
    public void CheckConnection() {
        conn = Sql.DbConnector();
        if (conn == null) {
            System.out.println("Connection to database failed.");
            System.exit(1);
        } else {
            System.out.println("Connected to Corendon database!");
        }

    }

}
