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
import javafx.geometry.Pos;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

    //de methode waarmee de elementen van het formulier op het scherm worden
    //gezet en geïnitialiseerd.
    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        CheckConnection();

        //Formulier
        GridPane lostForm = new GridPane();
        Label lostTitle = new Label("Lost Luggage form: ");

        //Corendon Logo
        HBox pictureRegion = new HBox();
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
        Label title = new Label("Form Luggage Loss");
        //title.setFont(UniSans);
        title.setTextFill(Color.web("#D81E05"));

        Label PassInfo = new Label("Passenger Information");
        //PassInfo.setFont(UniSansItalicsmall);
        PassInfo.setTextFill(Color.web("#00bce2"));

        Label luggageInfo = new Label("Luggage Information");
        //luggageInfo.setFont(UniSansItalicsmall);
        luggageInfo.setTextFill(Color.web("#00bce2"));

        Separator separator = new Separator();

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);

        Button labelCheck = new Button("check");
        
        //labelCheck.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 18");
        // Submit knop
        Button next = new Button();
        //next.setFont(UniSansItalicbig);
        next.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 18");
        next.setTextFill(Color.web("#ffffff"));

        next.setText("Submit");

        // Informatie plek van invullen
        Label iata = new Label("Airport IATA: ");
        //iata.setFont(OpenSans);
        TextField iataSearch = new TextField();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);

        // Informatie passagier
        Label gender = new Label("Gender: ");
        //gender.setFont(OpenSans);
        gender.setTextFill(Color.web("#333333"));
        ComboBox genderSet = new ComboBox(FXCollections.observableArrayList("Mr.", "Mrs."));
        genderSet.setPromptText("Gender");
        genderSet.setStyle("-fx-base:white;-fx-font-fill:#333333");

        Label name = new Label("Name: ");
        //name.setFont(OpenSans);
        name.setTextFill(Color.web("#333333"));
        TextField SurnameInput = new TextField();
        SurnameInput.setPromptText("Surname");
        TextField NameInput = new TextField();
        NameInput.setPromptText("Firstname");
        TextField prefixInput = new TextField();
        prefixInput.setPromptText("Prefix");
        prefixInput.setMaxWidth(90);

        Label address = new Label("Address: ");
        address.setTextFill(Color.web("#333333"));
        TextField streetInput = new TextField();
        TextField hnumberInput = new TextField();
        //address.setFont(OpenSans);
        streetInput.setPromptText("Street");
        hnumberInput.setPromptText("Housenr.");
        hnumberInput.setMaxWidth(90);

        Label place = new Label("Town/Place: ");
        place.setTextFill(Color.web("#333333"));
        TextField placeInput = new TextField();
        //place.setFont(OpenSans);
        placeInput.setPromptText("Town/Place");

        Label zipcode = new Label("Zip-code: ");
        zipcode.setTextFill(Color.web("#333333"));
        TextField zipcodeInput = new TextField();
        zipcodeInput.setMaxWidth(100);
        //zipcode.setFont(OpenSans);
        zipcodeInput.setPromptText("Zipcode");

        Label country = new Label("Country: ");
        country.setTextFill(Color.web("#333333"));
        TextField countryInput = new TextField();
        //country.setFont(OpenSans);
        ComboBox countryList = new ComboBox(FXCollections.observableArrayList("Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Barbados", "Belarus", "Belgium", "Belize", "Bosnia and Herzegovina", "Brazil", "Bulgaria", "Canada", "Cayman Islands", "Chile", "China", "Colombia", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Ecuador", "Egypt", "Estonia", "Finland", "France", "Georgia", "Germany", "Greece", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Japan", "Kazakhstan", "Latvia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Malta", "Mexico", "Moldova", "Monaco", "Morocco", "Netherlands", "Netherlands Antilles", "New Zealand", "Norway", "Poland", "Portugal", "Qatar", "Romania", "Russia", "San Marino", "Saudi Arabia", "Serbia and Montenegro", "Slovakia", "Slovenia", "Spain", "South Korea", "Suriname", "Sweden", "Switzerland", "Tunisia", "Turkey", "Ukraine", "United Arab Emirates", "United Kingdom", "United States,Uruguay"));
        countryList.getSelectionModel().select(57);
        countryList.setPromptText("Country");
        countryList.setStyle("-fx-base:white");

        Label contactDetails = new Label("Contact Details: ");
        contactDetails.setTextFill(Color.web("#333333"));
        TextField phonenrInput = new TextField();
        //contactDetails.setFont(OpenSans);
        phonenrInput.setPromptText("Phonenumber");

        Label email = new Label("E-mail: ");
        email.setTextFill(Color.web("#333333"));
        TextField emailInput = new TextField();
        //email.setFont(OpenSans);
        emailInput.setPromptText("E-mail (optional)");
        emailInput.setMaxWidth(400);

        Label bagLabel = new Label("Label number: ");
        bagLabel.setTextFill(Color.web("#333333"));
        TextField labelInput = new TextField();
        //bagLabel.setFont(OpenSans);
        labelInput.setPromptText("Label number");

        Label flightNr = new Label("Flight number: ");
        flightNr.setTextFill(Color.web("#333333"));
        TextField flightInput = new TextField("CND");
        //flightNr.setFont(OpenSans);
        flightInput.setPromptText("Flight number");
        flightInput.setMaxWidth(100);

        Label bagType = new Label("Luggage Type: ");
        bagType.setTextFill(Color.web("#333333"));
        ComboBox typeInput = new ComboBox(FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase", "Duffel Bag", "Water Container", "Other"));
        //bagLabel.setFont(OpenSans);
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle("-fx-base:white");

        Label brandName = new Label("Brand name: ");
        brandName.setTextFill(Color.web("#333333"));
        ComboBox brandList = new ComboBox(FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        //brandName.setFont(OpenSans);
        brandList.setPromptText("Brand name");
        brandList.setStyle("-fx-base:white");

        Label primaryColor = new Label("Primary Color: ");
        primaryColor.setTextFill(Color.web("#333333"));
        ComboBox priColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        //primaryColor.setFont(OpenSans);
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle("-fx-base:white");

        Label secondaryColor = new Label("Secondary Color(optional): ");
        secondaryColor.setTextFill(Color.web("#333333"));
        ComboBox secColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple"));
        //secondaryColor.setFont(OpenSans);
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle("-fx-base:white;");

        Label birthDate = new Label("Date of Birth: ");
        birthDate.setTextFill(Color.web("#333333"));
        DatePicker datePick = new DatePicker();
        datePick.setShowWeekNumbers(false);
        Label dateSet = new Label("(MM-DD-YYYY)");

        //dateSet.setFont(OpenSans);
        dateSet.setTextFill(Color.web("#D81E05"));
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("Date of Birth");

        Label moreInfo = new Label("Further Luggage Information: ");
        moreInfo.setTextFill(Color.web("#333333"));
        TextArea infoInput = new TextArea();
        //moreInfo.setFont(OpenSans);
        infoInput.setPromptText("Further Luggage Information... (optional)");
        infoInput.setMaxWidth(240);
        infoInput.setMaxHeight(150);

        ProgressBar stepsForm = new ProgressBar();
        stepsForm.setProgress(0.33F);
        stepsForm.setStyle("-fx-background-color:transparent;-fx-accent: #00bce2;");
        stepsForm.setPrefWidth(300);

        this.setHgap(15);
        this.setVgap(15);
        this.setPadding(new Insets(50, 30, 50, 30));

        //onderdelen toevoegen
        this.add(title, 1, 0);
        this.add(separator, 1, 1);
        this.add(next, 8, 16);
        this.add(PassInfo, 1, 4, 2, 1);
        this.add(iata, 1, 3);
        this.add(iataSearch, 2, 3);
        this.add(gender, 1, 5);
        this.add(genderSet, 2, 5);
        this.add(name, 1, 6);
        this.add(SurnameInput, 2, 7);
        this.add(NameInput, 2, 6);
        this.add(prefixInput, 3, 6);
        this.add(birthDate, 1, 8);
        this.add(datePick, 2, 8);
        this.add(dateSet, 3, 8);
        this.add(address, 1, 9);
        this.add(streetInput, 2, 9);
        this.add(hnumberInput, 3, 9);
        this.add(place, 1, 10);
        this.add(placeInput, 2, 10);
        this.add(zipcode, 1, 11);
        this.add(zipcodeInput, 2, 11);
        this.add(country, 1, 12);
        this.add(countryList, 2, 12);
        this.add(contactDetails, 1, 13);
        this.add(phonenrInput, 2, 13);
        this.add(emailInput, 3, 13);
        this.add(separator1, 5, 2, 10, 15);
        this.add(luggageInfo, 7, 3);
        this.add(bagLabel, 7, 4);
        this.add(labelInput, 8, 4);
        this.add(labelCheck, 9, 4);
        this.add(flightNr, 7, 5);
        this.add(flightInput, 8, 5);
        this.add(bagType, 7, 6);
        this.add(typeInput, 8, 6);
        this.add(brandName, 7, 7);
        this.add(brandList, 8, 7);
        this.add(primaryColor, 7, 8);
        this.add(priColorList, 8, 8);
        this.add(secondaryColor, 7, 9);
        this.add(secColorList, 8, 9);
        this.add(moreInfo, 7, 10);
        this.add(infoInput, 8, 10, 1, 6);

        this.setStyle("-fx-background-color: white");
        
        labelCheck.setOnAction((ActionEvent e) ->{
            checkLabel(primaryStage, labelInput.getText());
            System.out.println(labelInput.getText());
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
                String query = "INSERT INTO bagage"
                        + "(labelnr, vlucht, iata, lugType, merk, Prikleur, SecKleur, status, datum_bevestiging) VALUES"
                        + "(?,?,?,?,?,?,?,'lost',NOW())";
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

                pst2 = conn.prepareStatement(query2);
                pst2.setString(1, (String) genderSet.getSelectionModel().getSelectedItem());
                pst2.setString(2, NameInput.getText());
                pst2.setString(3, SurnameInput.getText());
                pst2.setString(4, ((TextField) datePick.getEditor()).getText());
                pst2.setString(5, streetInput.getText());
                pst2.setString(6, hnumberInput.getText());
                pst2.setString(7, placeInput.getText());
                pst2.setString(8, zipcodeInput.getText());
                pst2.setString(9, (String) countryList.getSelectionModel().getSelectedItem());
                pst2.setString(10, phonenrInput.getText());
                pst2.setString(11, emailInput.getText());

                pst.executeUpdate();
                pst2.executeUpdate();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Corendon - Luggage");
                alert.setHeaderText(null);
                alert.setContentText("Information successfully submitted");
                alert.showAndWait();

                System.out.println("Information submitted.");
            } catch (Exception e1) {

                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Corendon - Luggage");
                alert.setHeaderText(null);
                alert.setContentText("Some information is not filled in, please try again.");
                alert.showAndWait();

                System.out.println("SQL Error");
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

        try (Connection conn2 = Sql.DbConnector();) {
            String SQL = "SELECT * FROM bagage WHERE lost_id = " + "'" + labelnr + "'";
            ResultSet rs2 = conn2.createStatement().executeQuery(SQL);
            this.luggageData.clear();
            while (rs2.next()) {
                //if (rs.getString("lost_id").equals(labelnr)) {
                    this.luggageData.add(new LuggageRecord2(rs2.getString("lost_id"),
                            rs2.getString("labelnr"), rs2.getString("vlucht"),
                            rs2.getString("lugType"), rs2.getString("merk"),
                            rs2.getString("PriKleur"), rs2.getString("SecKleur"),
                            "", "", rs2.getString("status"),
                            rs2.getString("datum_bevestiging").substring(0, Math.min(rs2.getString("datum_bevestiging").length(), 9)),
                            rs2.getString("datum_bevestiging").substring(11, Math.min(rs2.getString("datum_bevestiging").length(), 18))));
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
        TableColumn statusCol = new TableColumn("status");
        TableColumn dateCol = new TableColumn("date");
        TableColumn timeCol = new TableColumn("time");

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
        controls.setPadding(new Insets(20, 20, 20, 20));
        
        controls.getChildren().addAll(solve, cancel);
        prompt.setPadding(new Insets(20, 20, 20, 20));
        prompt.getChildren().addAll(tableView, controls);
        Scene dialogScene = new Scene(prompt, 600, 200);
        checkPopup.setScene(dialogScene);
        checkPopup.show();
        
        for (LuggageRecord2 luggage : luggageData){
            System.out.print(luggage.toString());
        }
        

    }

}
