/*
 * De main applicatie
 */
package corendon;

/*
 TODO:

Tijmen:
- PDF, pdf producer bij registreer missing
- Maken Tab "Customer" (copy paste luggage en aanpassen)

Jeroen:
- Update registratie met extra informatie voor klant en bagage
- Exporteer PDF na oplossing van een case

Burak:
- Help knop
- Grafiek

Zouhar:
- Verwijderen van vernietigde bagage beschikbaar maken

Kenan:
- Reset wachtwoord, verander wachtwoord/user gegevens
(reset knop bij inlog scherm zet waarde van LostPassword in DB naar true)
(dus ook nieuwe kolom maken in DB bij users boolean LostPassword)
- Statussen worden missing/found/solved/delivered

*/




import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 *
 * @author JerryJerr
 */
public class Corendon extends Application {

    Connection conn;
    ResultSet rs = null;
    PreparedStatement pst = null;
    
    static String uname = "";
    static String mail = "";
    final String fieldStyle = "-fx-border-width: 1;\n" +
            "-fx-border-radius: 5;\n" +
            "-fx-border-color: #cccccc;\n" +
            "-fx-background-color: #ffffff;"+
                    "-fx-text-inner-color: #555555;";
    
    @Override
    public void start(Stage primaryStage) {
        CheckConnection(); //de methode CheckConnection() wordt uitgevoerd

        /*
        MissingForm.java
         */
        TabPane tabScreen = new TabPane(); //het hoofdscherm
        Tab luggage = new Tab("Luggage");
        Tab customers = new Tab("Customers");
        Tab missing = new Tab("Missing");
        Tab found   = new Tab("Found");
        Tab users   = new Tab("Users");
        Tab stats   = new Tab("Statistics");
        Tab setts   = new Tab("Settings");
        
        LuggageOverview luggageContent = new LuggageOverview();
        luggageContent.initScreen(primaryStage);
        CustomerOverview customerContent = new CustomerOverview();
        customerContent.initScreen();
        MissingForm missingContent = new MissingForm(); //ipv gridpane maken we een instantie onze eigen versie van gridpane.
        missingContent.initScreen(primaryStage); //hier roepen we de methode aan die alle elementen van het formulier toevoegd.
        FoundForm foundContent = new FoundForm();
        foundContent.initScreen(primaryStage);
        UsersOverview usersContent = new UsersOverview();
        usersContent.initScreen(primaryStage);
        Statistics statsContent = new Statistics();
        statsContent.initScreen(primaryStage);
        UserSettings settingsContent = new UserSettings();
        settingsContent.initScreen(primaryStage);
        
        Scene newscene = new Scene(tabScreen, 1500, 800, Color.rgb(0, 0, 0, 0)); //het hoofdscherm wordt hier weergegeven.
        /*
         */

        Label loginLabel = new Label("Enter your details.");

        //de knoppen op het login scherm
        Button login = new Button("Log in");
        Hyperlink help = new Hyperlink("Help");
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
                            primaryStage.setTitle("Welcome");
                            primaryStage.show();
                            uname = usrField.getText();
                            
                            if (!(rs.getString("function").contains("admin"))) {
                                users.setDisable(true);
                                stats.setDisable(true);
                                if ((rs.getString("function").contains("amgr"))) {
                                    stats.setDisable(false);
                                }
                            }
                            
                            
                            
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
                    primaryStage.setTitle("Welcome");
                    primaryStage.show();
                    uname = usrField.getText();
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
        loginScreen.add(help, 2,3);
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
        primaryStage.setResizable(false);
        primaryStage.show();
        /*
         */

        /*
        De tabs van het hoofdscherm
         */
        newscene.getStylesheets().add("resources/css/style.css");
        tabScreen.getTabs().addAll(luggage, customers, missing, found, users, stats, setts);
        tabScreen.setSide(Side.LEFT);
        luggage.setContent(luggageContent);
        luggage.setClosable(false);
        customers.setContent(customerContent);
        customers.setClosable(false);
        missing.setContent(missingContent);
        missing.setClosable(false);
        found.setContent(foundContent);
        found.setClosable(false);
        users.setContent(usersContent);
        users.setClosable(false);
        stats.setContent(statsContent);
        stats.setClosable(false);
        setts.setContent(settingsContent);
        setts.setClosable(false);
        
        
        help.setOnAction((ActionEvent e) -> {
            helpScreen(primaryStage);
        });
        
    }   
        

    

    public void helpScreen(Stage primaryStage) {
        
        GridPane helpPage = new GridPane();
        Button back = new Button("<- Back ");
        Button manual = new Button("User Manual");
        Label forgotPassword = new Label("Forgot Password?");
        forgotPassword.setTextFill(Color.web("#00bce2"));
        forgotPassword.setStyle("-fx-font: 18px UniSansRegular");
        TextField emailInput = new TextField();
        emailInput.setStyle(fieldStyle);
        Button passwordReset = new Button("Request New Password");
        emailInput.setPromptText("Fill your E-mail here");
        passwordReset.setStyle(";-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        Label statusRequest = new Label();
        Label emailLabel = new Label("E-mail: ");
        emailLabel.setStyle("-fx-text-fill:#D81E05");
        
        back.setOnAction(e-> {
            start(primaryStage);
        });
        
        manual.setOnAction(e-> {
            
        });
        helpPage.setStyle("-fx-background-color:white");
        helpPage.setHgap(15);
        helpPage.setVgap(15);
        helpPage.setPadding(new Insets(10, 5, 10, 5));

        helpPage.add(forgotPassword, 1, 0,5,1);
        helpPage.add(passwordReset, 1, 5,7,1);
        helpPage.add(emailLabel,1,3);
        helpPage.add(emailInput, 2,3,5,1);
        helpPage.add(statusRequest, 1, 6,7,1);
        helpPage.add(back, 1, 7,8,1);
        helpPage.add(manual, 5, 10,11,1);
        
        
        
        Scene helpScreen = new Scene(helpPage, 350, 350);
        primaryStage.setTitle("Login Help");
        primaryStage.setScene(helpScreen);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        
        passwordReset.setOnAction(e -> {
            PreparedStatement pst2 = null;
            try {
                String query = "select * from users where Email=?";
                
                pst = conn.prepareStatement(query);
                pst.setString(1, emailInput.getText());
                rs = pst.executeQuery();

                if (rs.next()) {
                    String query1 = "update users set lost_password = (1) where email = ?";
                    pst2 = conn.prepareStatement(query1);
                    mail = emailInput.getText();
                    pst2.setString(1, mail);
                    statusRequest.setText("The admin has been notified for a password reset.");
                    pst2.executeUpdate();
                } else {
                    statusRequest.setText("E-mail address is not found in the database.");
                }
                

                emailInput.clear();
                pst.close();
                rs.close();

            } catch (Exception e1) {
                System.err.println(e1);
            }
        });

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
