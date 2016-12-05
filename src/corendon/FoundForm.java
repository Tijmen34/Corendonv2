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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Zouhar Alladien
 */
public class FoundForm extends GridPane{
    
    
    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
    public void initScreen() {
    //Formulier
        GridPane foundForm = new GridPane();
        Label foundTitle= new Label("Found Luggage form: ");
        
        //Corendon Logo
        
        
        HBox pictureRegion = new HBox();
        //Corendon Fonts
          Font UniSans = 
            Font.loadFont(getClass()
                .getResourceAsStream("/FOnt/Uni Sans Bold.ttf"), 18);
          Font OpenSans = 
            Font.loadFont(getClass()
                .getResourceAsStream("/FOnt/OpenSans-Regular.ttf"), 14);
          Font UniSansItalicsmall = 
            Font.loadFont(getClass()
                .getResourceAsStream("/FOnt/Uni Sans Bold Italic.ttf"), 16);
          Font UniSansItalicbig = 
            Font.loadFont(getClass()
                .getResourceAsStream("/FOnt/Uni Sans Bold Italic.ttf"), 20);
       
        //Titel Form
        Label title = new Label("Form Luggage Found");
        //title.setFont(UniSans);
        title.setTextFill(Color.web("#D81E05"));
        
        Label generalInfo = new Label("General information");
        //PassInfo.setFont(UniSansItalicsmall);
        generalInfo.setTextFill(Color.web("#00bce2"));
        
        Label luggageLabelInfo = new Label("Label information");
        //PassInfo.setFont(UniSansItalicsmall);
        luggageLabelInfo.setTextFill(Color.web("#00bce2"));
        
        Label luggageInfo = new Label("Luggage Information");
        //luggageInfo.setFont(UniSansItalicsmall);
        luggageInfo.setTextFill(Color.web("#00bce2"));
        
        
        Separator separator = new Separator();
        
        
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        
        
        
        
        
        
        // Submit knop
        Button next = new Button();
        //next.setFont(UniSansItalicbig);
        next.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        next.setTextFill(Color.web("#ffffff"));
       
        next.setText("Submit");
        
        // Informatie plek van invullen
        Label iata= new Label("Airport IATA: ");
        //iata.setFont(OpenSans);
        TextField iataSearch = new TextField ();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        
        
        
        
        // Algemene informatie  
        Label date = new Label("Date: ");
        date.setTextFill(Color.web("#333333"));
        DatePicker datePick = new DatePicker();
        datePick.setShowWeekNumbers(false);
        //dateSet.setFont(OpenSans);
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("DD-MM-JJJJ");

        
        Label airport= new Label("Airport: ");
        airport.setTextFill(Color.web("#333333"));
        TextField airportInput = new TextField ();
        //airport.setFont(OpenSans);
        airportInput.setPromptText("Aiport");
        
        
        Label time= new Label("Time: ");
        time.setTextFill(Color.web("#333333"));
        TextField timeInput = new TextField ();
        //place.setFont(OpenSans);
        timeInput.setPromptText("00:00");
        

        //Label
        Label bagLabel= new Label("Label number: ");
        bagLabel.setTextFill(Color.web("#333333"));
        TextField labelInput = new TextField ();
        //bagLabel.setFont(OpenSans);
        labelInput.setPromptText("Label number");
        
        Label flightNr= new Label("Flight number: ");
        flightNr.setTextFill(Color.web("#333333"));
        TextField flightInput = new TextField ();
        //flightNr.setFont(OpenSans);
        flightInput.setPromptText("Flight number");
        flightInput.setMaxWidth(400);
        
         Label destination= new Label("Destination: ");
        //name.setFont(OpenSans);
        destination.setTextFill(Color.web("#333333"));
        TextField destinationInput = new TextField ();
        destinationInput.setPromptText("Destination");
        
        Label nameTraveler= new Label("Name traveler: ");
        nameTraveler.setTextFill(Color.web("#333333"));
        TextField surnameInput = new TextField ();
        //contactDetails.setFont(OpenSans);
        surnameInput.setPromptText("Surname");
        
        Label firstname = new Label("firstname: ");
        firstname.setTextFill(Color.web("#333333"));
        TextField firstNameInput = new TextField ();
        //email.setFont(OpenSans);
        firstNameInput.setPromptText("firstname");
        firstNameInput.setMaxWidth(400);
        
        //baggage
        Label bagType= new Label("Luggage Type: ");
        bagType.setTextFill(Color.web("#333333"));
        ComboBox typeInput = new ComboBox (FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase", "Duffel Bag", "Water Container", "Other"));
        //bagLabel.setFont(OpenSans);
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle("-fx-base:white");
        
        Label brandName= new Label("Brand name: ");
        brandName.setTextFill(Color.web("#333333"));
        ComboBox brandList = new ComboBox (FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        //brandName.setFont(OpenSans);
        brandList.setPromptText("Brand name");
        brandList.setStyle("-fx-base:white");
        
        Label primaryColor= new Label("Primary Color: ");
        primaryColor.setTextFill(Color.web("#333333"));
        ComboBox priColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        //primaryColor.setFont(OpenSans);
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle("-fx-base:white");
        
        Label secondaryColor= new Label("Secondary Color: ");
        secondaryColor.setTextFill(Color.web("#333333"));
        ComboBox secColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple"));
        //secondaryColor.setFont(OpenSans);
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle("-fx-base:white;");
        
        

        
        Label moreInfo = new Label("Further Luggage Information: ");
        moreInfo.setTextFill(Color.web("#333333"));
        TextArea infoInput = new TextArea();
        //moreInfo.setFont(OpenSans);
        infoInput.setPromptText("Further Luggage Information...");
        infoInput.setMaxWidth(240);
        
        
        
        ProgressBar stepsForm = new ProgressBar();
        stepsForm.setProgress(0.33F);
        stepsForm.setStyle("-fx-background-color:transparent;-fx-accent: #00bce2;");
        stepsForm.setPrefWidth(300);
        
        this.setHgap(15);
        this.setVgap(15);
        this.setPadding(new Insets(50, 30, 50, 30));
        
        //ONDERDELEN TOEVOEGEN
        
        this.add(title,1,0);
        this.add(separator, 1, 1);
        this.add(next,8, 14);
        //ALGEMENE
        this.add(generalInfo, 1, 4, 2, 1);
        this.add(iata, 1, 3);
        this.add(iataSearch, 2,3);
        this.add(date,1,5);
        this.add(datePick, 2, 5);
        this.add(time, 1, 6);
        this.add(timeInput, 2, 6);
        this.add(airport,1,7);
        this.add(airportInput,2,7);
        //BAGAGELABEL
        this.add(luggageLabelInfo, 1, 8,2,1);
        this.add(bagLabel, 1, 9);
        this.add(labelInput, 2,9);
        this.add(flightNr, 1, 10);
        this.add(flightInput, 2,10);
        this.add(destination, 1, 11);
        this.add(destinationInput, 2,11);
        this.add(nameTraveler, 1, 12);
        this.add(surnameInput, 2,12);
        this.add(firstNameInput, 3,12);
        this.add(separator1,5,2, 10,15);
        //BAGAGEINFO
        this.add(luggageInfo, 7, 3);
        this.add(bagType,7,4);
        this.add(typeInput,8,4);
        this.add(brandName,7,5);
        this.add(brandList,8,5);
        this.add(primaryColor,7,6);
        this.add(priColorList,8,6);
        this.add(secondaryColor,7,7);
        this.add(secColorList,8,7);
        this.add(moreInfo,7,8);
        this.add(infoInput,8,8,1,3);
        
        
        
        
       
        this.setStyle("-fx-background-color: white");
       
//        Scene scene = new Scene(lostForm, 1350, 700);
//        primaryStage.setTitle("Luggage - Lost Form");
//        primaryStage.getIcons().add(new Image("Logo.png"));
//        primaryStage.setScene(scene);
//        this.setResizable(false);
//        primaryStage.resizableProperty().setValue(Boolean.FALSE);
//        primaryStage.show();
        
        next.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        
        
        
    }
}

