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
 * @author JerryJerr
 */
public class MissingForm extends GridPane{
    
    
    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
    public void initScreen() {
        
        /*
        maten voor het grid
        */
        //Formulier
        GridPane lostForm = new GridPane();
        Label lostTitle= new Label("Lost Luggage form: ");
        
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
        Label title = new Label("Form Luggage Loss");
        title.setFont(UniSans);
        title.setTextFill(Color.web("#D81E05"));
        
        Label PassInfo = new Label("Passenger Information");
        PassInfo.setFont(UniSansItalicsmall);
        PassInfo.setTextFill(Color.web("#00bce2"));
        
        Label luggageInfo = new Label("Luggage Information");
        luggageInfo.setFont(UniSansItalicsmall);
        luggageInfo.setTextFill(Color.web("#00bce2"));
        
        
        Separator separator = new Separator();
        
        
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        
        
        
        
        
        
        // Submit knop
        Button next = new Button();
        next.setFont(UniSansItalicbig);
        next.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        next.setTextFill(Color.web("#ffffff"));
       
        next.setText("Submit");
        
        // Informatie plek van invullen
        Label iata= new Label("Airport IATA: ");
        iata.setFont(OpenSans);
        TextField iataSearch = new TextField ();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        
        
        
        
        // Informatie passagier
        Label gender= new Label("Gender: ");
        gender.setFont(OpenSans);
        gender.setTextFill(Color.web("#333333"));
        ComboBox genderSet = new ComboBox(FXCollections.observableArrayList("Mr.","Mrs."));
        genderSet.setPromptText("Gender");
        genderSet.setStyle("-fx-base:white;-fx-font-fill:#333333");
        
        Label name= new Label("Name: ");
        name.setFont(OpenSans);
        name.setTextFill(Color.web("#333333"));
        TextField SurnameInput = new TextField ();
        SurnameInput.setPromptText("Surname");
        TextField NameInput = new TextField ();
        NameInput.setPromptText("Firstname");
        
        Label address= new Label("Address: ");
        address.setTextFill(Color.web("#333333"));
        TextField streetInput = new TextField ();
        TextField hnumberInput = new TextField();
        address.setFont(OpenSans);
        streetInput.setPromptText("Street");
        hnumberInput.setPromptText("Housenr.");
        hnumberInput.setMaxWidth(90);
        
        Label place= new Label("Town/Place: ");
        place.setTextFill(Color.web("#333333"));
        TextField placeInput = new TextField ();
        place.setFont(OpenSans);
        placeInput.setPromptText("Town/Place");
        
        Label zipcode= new Label("Zip-code: ");
        zipcode.setTextFill(Color.web("#333333"));
        TextField zipcodeInput = new TextField ();
        zipcodeInput.setMaxWidth(100);
        zipcode.setFont(OpenSans);
        zipcodeInput.setPromptText("Zipcode");
        
        Label country= new Label("Country: ");
        country.setTextFill(Color.web("#333333"));
        TextField countryInput = new TextField ();
        country.setFont(OpenSans);
        ComboBox countryList = new ComboBox(FXCollections.observableArrayList("Austria", "Germany", "Netherlands", "Turkey", "Spain"));
        countryList.getSelectionModel().select(2);
        countryList.setPromptText("Country");
        countryList.setStyle("-fx-base:white");
        
        Label contactDetails= new Label("Contact Details: ");
        contactDetails.setTextFill(Color.web("#333333"));
        TextField phonenrInput = new TextField ();
        contactDetails.setFont(OpenSans);
        phonenrInput.setPromptText("Phonenumber");
        
        Label email= new Label("E-mail: ");
        email.setTextFill(Color.web("#333333"));
        TextField emailInput = new TextField ();
        email.setFont(OpenSans);
        emailInput.setPromptText("E-mail");
        emailInput.setMaxWidth(400);
        
        Label bagLabel= new Label("Label number: ");
        bagLabel.setTextFill(Color.web("#333333"));
        TextField labelInput = new TextField ();
        bagLabel.setFont(OpenSans);
        labelInput.setPromptText("Label number");
        
        Label flightNr= new Label("Flight number: ");
        flightNr.setTextFill(Color.web("#333333"));
        TextField flightInput = new TextField ("CND");
        flightNr.setFont(OpenSans);
        flightInput.setPromptText("Flight number");
        flightInput.setMaxWidth(100);
        
        Label bagType= new Label("Luggage Type: ");
        bagType.setTextFill(Color.web("#333333"));
        ComboBox typeInput = new ComboBox (FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase", "Duffel Bag", "Water Container", "Other"));
        bagLabel.setFont(OpenSans);
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle("-fx-base:white");
        
        Label brandName= new Label("Brand name: ");
        brandName.setTextFill(Color.web("#333333"));
        ComboBox brandList = new ComboBox (FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        brandName.setFont(OpenSans);
        brandList.setPromptText("Brand name");
        brandList.setStyle("-fx-base:white");
        
        Label primaryColor= new Label("Primary Color: ");
        primaryColor.setTextFill(Color.web("#333333"));
        ComboBox priColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        primaryColor.setFont(OpenSans);
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle("-fx-base:white");
        
        Label secondaryColor= new Label("Secondary Color: ");
        secondaryColor.setTextFill(Color.web("#333333"));
        ComboBox secColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple"));
        secondaryColor.setFont(OpenSans);
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle("-fx-base:white;");
        
        
        Label birthDate = new Label("Date of Birth: ");
        birthDate.setTextFill(Color.web("#333333"));
        DatePicker datePick = new DatePicker();
        datePick.setShowWeekNumbers(false);
        Label dateSet = new Label("(DD-MM-JJJJ)");
        dateSet.setFont(OpenSans);
        dateSet.setTextFill(Color.web("#D81E05"));
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("Date of Birth");
        
        Label moreInfo = new Label("Further Luggage Information: ");
        moreInfo.setTextFill(Color.web("#333333"));
        TextArea infoInput = new TextArea();
        moreInfo.setFont(OpenSans);
        infoInput.setPromptText("Further Luggage Information...");
        infoInput.setMaxWidth(240);
        
        
        
        ProgressBar stepsForm = new ProgressBar();
        stepsForm.setProgress(0.33F);
        stepsForm.setStyle("-fx-background-color:transparent;-fx-accent: #00bce2;");
        stepsForm.setPrefWidth(300);
        
        lostForm.setHgap(15);
        lostForm.setVgap(15);
        lostForm.setPadding(new Insets(50, 30, 50, 30));
        
        //onderdelen toevoegen
        
        lostForm.add(title,1,0);
        lostForm.add(separator, 1, 1);
        lostForm.add(next,12, 14);
        lostForm.add(PassInfo,1,4);
        lostForm.add(iata, 1, 3);
        lostForm.add(iataSearch, 2,3);
        lostForm.add(gender,1,5);
        lostForm.add(genderSet, 2, 5);
        lostForm.add(name, 1, 6);
        lostForm.add(SurnameInput, 2, 6);
        lostForm.add(NameInput, 3, 6);
        lostForm.add(birthDate,1,7);
        lostForm.add(datePick,2,7);
        lostForm.add(dateSet,3,7);
        lostForm.add(address, 1, 8);
        lostForm.add(streetInput, 2,8);
        lostForm.add(hnumberInput,3,8);
        lostForm.add(place, 1, 9);
        lostForm.add(placeInput, 2,9);
        lostForm.add(zipcode, 1, 10);
        lostForm.add(zipcodeInput, 2,10);
        lostForm.add(country, 1, 11);
        lostForm.add(countryList, 2,11);
        lostForm.add(contactDetails, 1, 12);
        lostForm.add(phonenrInput, 2,12);
        lostForm.add(emailInput, 3,12);
        lostForm.add(separator1,5,2, 10,15);
        lostForm.add(luggageInfo, 7, 3);
        lostForm.add(bagLabel, 7,4);
        lostForm.add(labelInput,8,4);
        lostForm.add(flightNr, 7, 5);
        lostForm.add(flightInput,8,5);
        lostForm.add(bagType,7,6);
        lostForm.add(typeInput,8,6);
        lostForm.add(brandName,7,7);
        lostForm.add(brandList,8,7);
        lostForm.add(primaryColor,7,8);
        lostForm.add(priColorList,8,8);
        lostForm.add(secondaryColor,7,9);
        lostForm.add(secColorList,8,9);
        lostForm.add(moreInfo,7,10);
        lostForm.add(infoInput,8,10,1,3);
        
        
       
        lostForm.setStyle("-fx-background-color: white");
       
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

