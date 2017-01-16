/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import corendon.LuggageOverview;
import corendon.Corendon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.DateCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;

/**
 *
 * @author iS109 - 3
 */
public class MissingForm extends GridPane {

    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection conn;
    Statement stmt;
    private Stage primaryStage;
    private ObservableList<LuggageRecord2> luggageData
            = FXCollections.observableArrayList();

    private Button solve = new Button("Solve");
    private Button cancel = new Button("Cancel");
    
    
    //TextField styling
    final String fieldStyle = "-fx-border-width: 1;\n" +
            "-fx-border-radius: 5;\n" +
            "-fx-border-color: #cccccc;\n" +
            "-fx-background-color: #ffffff;"+
                    "-fx-text-inner-color: #555555;";
    
      
    

    //de methode waarmee de elementen van het formulier op het scherm worden
    //gezet en geïnitialiseerd.
    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        CheckConnection();

        
        
        //Corendon Logo
        HBox hbox = new HBox();
        hbox.setStyle("-fx-background-color: white;");
        hbox.setSpacing(0);
        hbox.setAlignment(Pos.TOP_RIGHT);
        Image corLogo = new Image("Corendon.png");
        ImageView logo = new ImageView();
         logo.setImage(corLogo);
         logo.setFitWidth(200);
         logo.setPreserveRatio(true);
         logo.setSmooth(true);
         logo.setCache(true);
        hbox.getChildren().addAll(logo);
        
        
        //Corendon Fonts
        Font UniSans
                = Font.loadFont(getClass()
                        .getResourceAsStream("/FOnt/Uni Sans Bold.ttf"), 18);
        Font OpenSans
                = Font.loadFont(getClass()
                        .getResourceAsStream("/FOnt/OpenSans-Regular.ttf"), 14);
        Font UniSansItalicsmall
                = Font.loadFont(getClass()
                        .getResourceAsStream("/FOnt/Uni Sans Bold Italic.ttf"), 16);
        Font UniSansItalicbig
                = Font.loadFont(getClass()
                        .getResourceAsStream("/FOnt/Uni Sans Bold Italic.ttf"), 20);

        //Titel Form
        Label title = new Label("\t Form Luggage Loss");
        //title.setFont(UniSans);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-font: 20px UniSansSemiBold");
        
        Rectangle titleBox = new Rectangle();
        
        titleBox.setWidth(2000);
        titleBox.setHeight(50);
        titleBox.setFill(Color.web("#D81E05"));
        

        Label PassInfo = new Label("Passenger Information");
        //PassInfo.setFont(UniSansItalicsmall);
        PassInfo.setTextFill(Color.web("#00bce2"));
        PassInfo.setStyle("-fx-font: 18px UniSansRegular");
        

        Label luggageInfo = new Label("Luggage Information");
        //luggageInfo.setFont(UniSansItalicsmall);
        luggageInfo.setTextFill(Color.web("#00bce2"));
        luggageInfo.setStyle("-fx-font: 18px UniSansRegular");

       

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        
        //Check Label knop
        Button labelCheck = new Button("check");
        //next.setFont(UniSansItalicbig);
        labelCheck.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 14; -fx-font-weight: bold");
        labelCheck.setTextFill(Color.web("#ffffff"));
        labelCheck.setText("Check");
        labelCheck.setMaxWidth(120);

        
        // Submit knop
        Button next = new Button();
        //next.setFont(UniSansItalicbig);
        next.setStyle("-fx-font: 20px UniSansBoldItalic;-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;");
        next.setTextFill(Color.web("#ffffff"));
        next.setText("Submit");
        

        // Informatie plek van invullen
        Label iata = new Label("Airport IATA: ");
        TextField iataSearch = new TextField();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        iataSearch.setStyle(fieldStyle);
        final int IATALIMIT = 3;
        
        iataSearch.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (iataSearch.getText().length() > IATALIMIT) {
                String s = iataSearch.getText().substring(0, IATALIMIT);
                iataSearch.setText(s);
            }
        }
    });
        
        
        
        Label blank = new Label("");
        
        
        
        
        // Informatie passagier
        Label gender = new Label("Gender: ");
        //gender.setFont(OpenSans);
        gender.setTextFill(Color.web("#333333"));
        ComboBox genderSet = new ComboBox(FXCollections.observableArrayList("Mr.", "Mrs."));
        genderSet.setPromptText("Gender");
        genderSet.setStyle(fieldStyle);

        Label name = new Label("Name: ");
        //name.setFont(OpenSans);
        name.setTextFill(Color.web("#333333"));
        TextField SurnameInput = new TextField();
        SurnameInput.setPromptText("Surname");
        SurnameInput.setStyle(fieldStyle);
        
        TextField NameInput = new TextField();
        NameInput.setPromptText("Firstname");
        NameInput.setStyle(fieldStyle);
        
        TextField prefixInput = new TextField();
        prefixInput.setPromptText("Prefix");
        prefixInput.setMaxWidth(90);
        prefixInput.setStyle(fieldStyle);
        
        final int PREFIXLIMIT = 10;
        prefixInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (prefixInput.getText().length() > PREFIXLIMIT) {
                String s = prefixInput.getText().substring(0, PREFIXLIMIT);
                prefixInput.setText(s);
            }
        }
    });

        Label address = new Label("Address: ");
        address.setTextFill(Color.web("#333333"));
        TextField streetInput = new TextField();
        TextField hnumberInput = new TextField();
        //address.setFont(OpenSans);
        streetInput.setPromptText("Street");
        streetInput.setStyle(fieldStyle);
        hnumberInput.setPromptText("Housenr.");
        hnumberInput.setStyle(fieldStyle);
        hnumberInput.setMaxWidth(90);
        
        final int HNUMBERLIMIT = 6;
        hnumberInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (hnumberInput.getText().length() > HNUMBERLIMIT) {
                String s = hnumberInput.getText().substring(0, HNUMBERLIMIT);
                hnumberInput.setText(s);
            }
        }
    });

        Label place = new Label("Town/Place: ");
        place.setTextFill(Color.web("#333333"));
        TextField placeInput = new TextField();
        //place.setFont(OpenSans);
        placeInput.setPromptText("Town/Place");
        placeInput.setStyle(fieldStyle);

        Label zipcode = new Label("Zip-code: ");
        zipcode.setTextFill(Color.web("#333333"));
        TextField zipcodeInput = new TextField();
        zipcodeInput.setMaxWidth(100);
        //zipcode.setFont(OpenSans);
        zipcodeInput.setPromptText("Zipcode");
        zipcodeInput.setStyle(fieldStyle);
        
        final int ZIPCODELIMIT = 7;
        
        zipcodeInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (zipcodeInput.getText().length() > ZIPCODELIMIT) {
                String s = zipcodeInput.getText().substring(0, ZIPCODELIMIT);
                zipcodeInput.setText(s);
            }
        }
    });
        

        Label country = new Label("Country: ");
        country.setTextFill(Color.web("#333333"));
        ComboBox countryList = new ComboBox(FXCollections.observableArrayList("Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan","Bahamas","Barbados","Belarus","Belgium","Belize","Bosnia and Herzegovina","Brazil","Bulgaria","Canada","Cayman Islands","Chile","China","Colombia","Costa Rica","Cote d'Ivoire","Croatia","Cuba","Cyprus","Czech Republic","Denmark","Ecuador","Egypt","Estonia","Finland","France","Georgia","Germany","Greece","Hong Kong","Hungary","Iceland","India","Indonesia","Iran","Iraq","Ireland","Israel","Italy","Japan","Kazakhstan","Latvia","Libya","Liechtenstein","Lithuania","Luxembourg","Macedonia","Malta","Mexico","Moldova","Monaco","Morocco","Netherlands","Netherlands Antilles","New Zealand","Norway","Poland","Portugal","Qatar","Romania","Russia","San Marino","Saudi Arabia","Serbia and Montenegro","Slovakia","Slovenia","Spain","South Korea","Suriname","Sweden","Switzerland","Tunisia","Turkey","Ukraine","United Arab Emirates","United Kingdom","United States","Uruguay"));
        countryList.setValue("Netherlands");
       
        countryList.setPromptText("Country");
        countryList.setStyle(fieldStyle);
        
        Label contactDetails = new Label("Contact Details: ");
        contactDetails.setTextFill(Color.web("#333333"));
        TextField phonenrInput = new TextField();
        //contactDetails.setFont(OpenSans);
        phonenrInput.setPromptText("Phonenumber");
        phonenrInput.setStyle(fieldStyle);
        
        final int PHONELIMIT = 20;
        phonenrInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (phonenrInput.getText().length() > PHONELIMIT) {
                String s = phonenrInput.getText().substring(0, PHONELIMIT);
                phonenrInput.setText(s);
            }
        }
    });

        
        TextField emailInput = new TextField();
        //email.setFont(OpenSans);
        emailInput.setPromptText("E-mail (optional)");
        emailInput.setStyle(fieldStyle);
        emailInput.setMaxWidth(400);

        Label bagLabel = new Label("Label number: ");
        
        TextField labelInput = new TextField();
        //bagLabel.setFont(OpenSans);
        labelInput.setPromptText("Label number");
        labelInput.setStyle(fieldStyle);
        labelInput.setMaxWidth(120);
        
        final int LABELLIMIT = 10;
        labelInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (labelInput.getText().length() > LABELLIMIT) {
                String s = labelInput.getText().substring(0, LABELLIMIT);
                labelInput.setText(s);
            }
        }
    });

        Label flightNr = new Label("Flight number: ");
        flightNr.setTextFill(Color.web("#333333"));
        TextField flightInput = new TextField("CND");
        //flightNr.setFont(OpenSans);
        flightInput.setPromptText("Flight number");
        flightInput.setStyle(fieldStyle);
        flightInput.setMaxWidth(100);
        
        final int FLIGHTLIMIT = 7;
        flightInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (flightInput.getText().length() > FLIGHTLIMIT) {
                String s = flightInput.getText().substring(0, FLIGHTLIMIT);
                flightInput.setText(s);
            }
        }
    });

        Label bagType = new Label("Luggage Type: ");
        bagType.setTextFill(Color.web("#333333"));
        ComboBox typeInput = new ComboBox(FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase", "Duffel Bag", "Water Container", "Other"));
        //bagLabel.setFont(OpenSans);
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle(fieldStyle);
        

        Label brandName = new Label("Brand name: ");
        brandName.setTextFill(Color.web("#333333"));
        ComboBox brandList = new ComboBox(FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        //brandName.setFont(OpenSans);
        brandList.setPromptText("Brand name");
        brandList.setStyle(fieldStyle);
        
        Label primaryColor = new Label("Primary Color: ");
        primaryColor.setTextFill(Color.web("#333333"));
        ComboBox priColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        //primaryColor.setFont(OpenSans);
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle(fieldStyle);

        Label secondaryColor = new Label("Secondary Color (optional): ");
        secondaryColor.setTextFill(Color.web("#333333"));
        ComboBox secColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple"));
        //secondaryColor.setFont(OpenSans);
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle("-fx-base:white;");
        secColorList.setStyle(fieldStyle);

        Label birthDate = new Label("Date of Birth: ");
        birthDate.setTextFill(Color.web("#333333"));
        DatePicker datePick = new DatePicker();
        datePick.setValue(LocalDate.now().minusYears(18));
        datePick.setShowWeekNumbers(false);
        Label dateSet = new Label("(MM-DD-YYYY)");
        dateSet.setTextFill(Color.web("#D81E05"));
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("Date of Birth");
        datePick.setStyle(fieldStyle);
        

        Label moreInfo = new Label("Further Luggage Information: ");
        moreInfo.setTextFill(Color.web("#333333"));
        TextArea infoInput = new TextArea();
        infoInput.setPromptText("Further Luggage Information... (optional)");
        infoInput.setMaxWidth(240);
        infoInput.setMaxHeight(150);
        infoInput.setStyle("");
        
        final int INFOLIMIT = 100;
        
        infoInput.textProperty().addListener(new ChangeListener<String>() {
        public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
            if (infoInput.getText().length() > INFOLIMIT) {
                String s = infoInput.getText().substring(0, INFOLIMIT);
                infoInput.setText(s);
            }
        }
    });
       

        this.setHgap(15);
        this.setVgap(15);
        this.setPadding(new Insets(30, 30, 30, 30));
        this.add(blank,8,1);

        //onderdelen toevoegen
        this.add(hbox,0,0,16,1);
        this.add(titleBox,1,1,18,1);
        this.add(title, 1, 1,1,1);
        this.add(next, 8, 17);
        this.add(PassInfo, 1, 5, 2, 1);
        this.add(iata, 1, 4);
        this.add(iataSearch, 2, 4);
        this.add(gender, 1, 6);
        this.add(genderSet, 2, 6);
        this.add(name, 1, 7);
        this.add(SurnameInput, 2, 8);
        this.add(NameInput, 2, 7);
        this.add(prefixInput, 3, 7);
        this.add(birthDate, 1, 9);
        this.add(datePick, 2, 9);
        this.add(dateSet, 3, 9);
        this.add(address, 1, 10);
        this.add(streetInput, 2, 10);
        this.add(hnumberInput, 3, 10);
        this.add(place, 1, 11);
        this.add(placeInput, 2, 11);
        this.add(zipcode, 1, 12);
        this.add(zipcodeInput, 2, 12);
        this.add(country, 1, 13);
        this.add(countryList, 2, 13);
        this.add(contactDetails, 1, 14);
        this.add(phonenrInput, 2, 14);
        this.add(emailInput, 3, 14);
        this.add(separator1, 5, 3, 10, 15);
        this.add(luggageInfo, 7, 4);
        this.add(bagLabel, 7, 5);
        this.add(labelInput, 9, 5);
        this.add(labelCheck, 9, 5,1,3);
        this.add(flightNr, 7, 7);
        this.add(flightInput, 9, 7);
        this.add(bagType, 7, 8);
        this.add(typeInput, 9, 8);
        this.add(brandName, 7, 9);
        this.add(brandList, 9, 9);
        this.add(primaryColor, 7, 10);
        this.add(priColorList, 9, 10);
        this.add(secondaryColor, 7, 11);
        this.add(secColorList, 9, 11);
        this.add(moreInfo, 7, 12);
        this.add(infoInput, 9, 12, 1, 4);
        
        

        this.setStyle("-fx-background-color: white;-fx-font-family: Open Sans;-fx-fill: red");

        labelCheck.setOnAction((ActionEvent e) -> {
            checkLabel(primaryStage, labelInput.getText());
        });

//        Scene scene = new Scene(lostForm, 1350, 700);
//        primaryStage.setTitle("Luggage - Lost Form");
//        primaryStage.getIcons().add(new Image("Logo.png"));
//        primaryStage.setScene(scene);
//        this.setResizable(false);
//        primaryStage.resizableProperty().setValue(Boolean.FALSE);
//        primaryStage.show();
        next.setOnAction((ActionEvent e) -> {
            PreparedStatement pst2 = null;
                try {
                    
                if(labelInput.getText().isEmpty()|| typeInput.getSelectionModel().isEmpty() || priColorList.getSelectionModel().isEmpty() ||brandList.getSelectionModel().isEmpty() || flightInput.getText().isEmpty() || SurnameInput.getText().isEmpty() || NameInput.getText().isEmpty() || datePick.getEditor().getText().isEmpty() || streetInput.getText().isEmpty() || hnumberInput.getText().isEmpty() || placeInput.getText().isEmpty() || zipcodeInput.getText().isEmpty() || phonenrInput.getText().isEmpty()){               
                 Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Corendon - Luggage");
                    alert.setHeaderText(null);
                    alert.setContentText("Some information is not filled in, please try again.");
                    alert.showAndWait();
                System.out.println("Some information is not filled in");
                }else{
                    
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Corendon - Luggage");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Information successfully submitted");
                    alert1.showAndWait();
                System.out.println("Information submitted.");
                
                    String query = "INSERT INTO bagage"
				+ "(labelnr, vlucht, iata, lugType, merk, Prikleur, SecKleur, extra_info, status, datum_bevestiging) VALUES"
				+ "(?,?,?,?,?,?,?,?,'lost',NOW())";
                    String query2 = "INSERT INTO klant"
				+ "(geslacht, naam, tussenvoegsel, achternaam, gebdatum, straat, huisnummer,"
                            + " plaats, postcode, land, telnr, mail, datum_bevestiging) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?, NOW())";
                    pst = conn.prepareStatement(query);
                    pst.setString(1, labelInput.getText());
                    pst.setString(2, flightInput.getText());
                    pst.setString(3, iataSearch.getText());
                    pst.setString(5, (String) brandList.getSelectionModel().getSelectedItem());
                    pst.setString(6, (String) priColorList.getSelectionModel().getSelectedItem());
                    pst.setString(7, (String) secColorList.getSelectionModel().getSelectedItem());
                    pst.setString(4, (String) typeInput.getSelectionModel().getSelectedItem());
                    pst.setString(8, infoInput.getText());
                    
                    
                    pst2 = conn.prepareStatement(query2);
                    pst2.setString(1, (String) genderSet.getSelectionModel().getSelectedItem());
                    pst2.setString(2, NameInput.getText());
                    pst2.setString(3, prefixInput.getText());
                    pst2.setString(4, SurnameInput.getText());
                    pst2.setString(5, ((TextField)datePick.getEditor()).getText());
                    pst2.setString(6, streetInput.getText());
                    pst2.setString(7, hnumberInput.getText());
                    pst2.setString(8, placeInput.getText());
                    pst2.setString(9, zipcodeInput.getText());
                    pst2.setString(10, (String) countryList.getSelectionModel().getSelectedItem());
                    pst2.setString(11, phonenrInput.getText());
                    pst2.setString(12, emailInput.getText());

                pst.executeUpdate();
                pst2.executeUpdate();
                
                
                    
                }
                
                }
                catch (Exception e1) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Corendon - Luggage");
                    alert.setHeaderText(null);
                    alert.setContentText("There is an error in the database, please try again later.");
                    alert.showAndWait();
                System.out.println("SQL ERROR");
                System.err.println(e1);
                    
            }
        });
    }

    //check van tevoren de db verbinding
    public void CheckConnection() {
        conn = Sql.DbConnector();
        if (conn == null) {
            System.out.println("Connection lost.");
            System.exit(1);
        }
    }

    public void checkLabel(Stage primaryStage, String labelnr) {

        //records met zelfde labelnr ophalen
        try (Connection conn = Sql.DbConnector();) {
            String query = "SELECT * FROM bagage WHERE labelnr=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, labelnr);
            ResultSet rs = pst.executeQuery();

            //jeroen wat doe je kil
            this.luggageData.clear();
            while (rs.next()) {
                System.out.println("test 1: " + rs.getString("lost_id"));
                //if (rs.getString("lost_id").equals(labelnr)) {
                this.luggageData.add(new LuggageRecord2(rs.getString("lost_id"),
                        rs.getString("labelnr"), rs.getString("vlucht"),
                        rs.getString("lugType"), rs.getString("merk"),
                        rs.getString("PriKleur"), rs.getString("SecKleur"),
                        "", "", rs.getString("status"),
                        rs.getString("datum_bevestiging").substring(0, Math.min(rs.getString("datum_bevestiging").length(), 9)),
                        rs.getString("datum_bevestiging").substring(11, Math.min(rs.getString("datum_bevestiging").length(), 18))));
                //}
            }
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }

        //TableView
        final TableView<LuggageRecord2> tableView = new TableView();

        TableColumn lostIdCol = new TableColumn("Lost ID");
        TableColumn labelNrCol = new TableColumn("Label nr");
        TableColumn flightNrCol = new TableColumn("Flight nr");
        TableColumn typeCol = new TableColumn("Type");
        TableColumn brandCol = new TableColumn("Brand Name");
        TableColumn primaryColorCol = new TableColumn("Color 1");
        TableColumn secondaryColorCol = new TableColumn("Color 2");
        TableColumn infoCol = new TableColumn("Add. info");
        TableColumn customerIdCol = new TableColumn("Customer ID");
        TableColumn statusCol = new TableColumn("Status");
        TableColumn dateCol = new TableColumn("Date");
        TableColumn timeCol = new TableColumn("Time");

        lostIdCol.setCellValueFactory(
                new PropertyValueFactory<>("lostId"));
        labelNrCol.setCellValueFactory(
                new PropertyValueFactory<>("labelNr"));
        flightNrCol.setCellValueFactory(
                new PropertyValueFactory<>("flightNr"));
        typeCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brandName"));
        primaryColorCol.setCellValueFactory(
                new PropertyValueFactory<>("primaryColor"));
        secondaryColorCol.setCellValueFactory(
                new PropertyValueFactory<>("secondaryColor"));
        infoCol.setCellValueFactory(
                new PropertyValueFactory<>("info"));
        customerIdCol.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));
        statusCol.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        dateCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(
                new PropertyValueFactory<>("time"));

        tableView.getColumns().addAll(lostIdCol, labelNrCol, flightNrCol,
                typeCol, brandCol, primaryColorCol, secondaryColorCol, infoCol,
                customerIdCol, statusCol, dateCol, timeCol);

        tableView.setItems(this.luggageData);

        //prompt
        final Stage checkPopup = new Stage();
        checkPopup.initModality(Modality.APPLICATION_MODAL);
        checkPopup.initOwner(primaryStage);
        HBox prompt = new HBox(20);
        VBox controls = new VBox(20);
        controls.setPadding(new Insets(5, 5,5,5));
        solve.setMinSize(90, 30);
        solve.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 20; -fx-font-weight: bold;");
        solve.setTextFill(Color.web("#ffffff"));
        solve.setText("Solve");
        cancel.setMinSize(70, 20);
        cancel.setStyle("-fx-base:white;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 12;");
        cancel.setTextFill(Color.web("#000000"));
        cancel.setText("Cancel");
        

        controls.getChildren().addAll(solve, cancel);
        prompt.setPadding(new Insets(0, 0, 0, 0));
        prompt.getChildren().addAll(tableView, controls);
        Scene dialogScene = new Scene(prompt, 1200, 200);
        checkPopup.setScene(dialogScene);
        checkPopup.show();

        solve.setOnAction((ActionEvent e) -> {
            solveFromPrompt(tableView);
        });
        
        cancel.setOnAction((ActionEvent e) -> {
            checkPopup.close();
        });
        //test
        for (LuggageRecord2 luggage : luggageData) {
            System.out.print(luggage.toString());
        }

    }

    public void solveFromPrompt(TableView tableView) {

        if (tableView.getSelectionModel().getSelectedCells().size() > 0) {
            try (Connection conn = Sql.DbConnector();) {
                String id = luggageData.get(tableView.getSelectionModel().getSelectedIndex()).getLostId();
                String SQL = "UPDATE bagage SET status = 'solved' WHERE lost_id = " + "'" + id + "'";
                System.out.println(SQL);
                conn.createStatement().executeUpdate(SQL);
                
                luggageData.remove(tableView.getSelectionModel().getSelectedIndex());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
            
        }
    }
    
    
    

}
