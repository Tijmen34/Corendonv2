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
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TableView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author iS109 - 3
 */
public class MissingForm extends GridPane {

    //private DbManager dbManager;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn;
    private Statement stmt;
    private Stage primaryStage;
    private ObservableList<LuggageRecord2> luggageData
            = FXCollections.observableArrayList();

    private Button solve = new Button("Solve");
    private Button cancel = new Button("Cancel");

    private Image corLogo = new Image("Corendon.png");
    private ImageView logo = new ImageView();
    private HBox hbox = new HBox();
    
    private final int PREFIXLIMIT = 10;
    private final int IATALIMIT = 3;
    private final int HNUMBERLIMIT = 6;
    private final int ZIPCODELIMIT = 7;
    private final int PHONELIMIT = 20;
    private final int LABELLIMIT = 10;
    private final int FLIGHTLIMIT = 7;
    private final int INFOLIMIT = 100;

    //TextField styling
    final String fieldStyle = "-fx-border-width: 1;\n"
            + "-fx-border-radius: 5;\n"
            + "-fx-border-color: #cccccc;\n"
            + "-fx-background-color: #ffffff;"
            + "-fx-text-inner-color: #555555;";

    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        CheckConnection();

        //Corendon Logo in hbox
        hbox.setSpacing(0);
        hbox.setAlignment(Pos.TOP_RIGHT);
        logo.setImage(corLogo);
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setCache(true);
        hbox.getChildren().addAll(logo);

        //Titel Form
        Label title = new Label("\t Form Luggage Loss");
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-font: 20px UniSansSemiBold");

        //rode streep door midden met form
        Rectangle titleBox = new Rectangle();
        titleBox.setWidth(2000);
        titleBox.setHeight(50);
        titleBox.setFill(Color.web("#D81E05"));

        Label PassInfo = new Label("Passenger Information");
        PassInfo.setTextFill(Color.web("#00bce2"));
        PassInfo.setStyle("-fx-font: 18px UniSansRegular");

        Label luggageInfo = new Label("Luggage Information");
        luggageInfo.setTextFill(Color.web("#00bce2"));
        luggageInfo.setStyle("-fx-font: 18px UniSansRegular");

        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);

        //Check Label knop
        Button labelCheck = new Button("Check");
        labelCheck.setStyle("-fx-base:#56ad3e;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-font-size: 14; "
                + "-fx-font-weight: bold");
        labelCheck.setTextFill(Color.web("#ffffff"));
        labelCheck.setMaxWidth(120);

        // Submit knop
        Button submit = new Button();
        submit.setStyle("-fx-font: 20px UniSansBoldItalic;"
                + "-fx-base:#56ad3e;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;");
        submit.setTextFill(Color.web("#ffffff"));
        submit.setText("Submit");

        // iata invullen (label en txtfield)
        Label iata = new Label("Airport IATA: ");
        TextField iataSearch = new TextField();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        iataSearch.setStyle(fieldStyle);

        //iata niet meer dan 3  tekens
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
        //deze spreken voor zichzelf
        Label gender = new Label("Gender: ");
        ComboBox genderSet = new ComboBox(FXCollections.observableArrayList("Mr.", "Mrs."));
        genderSet.setPromptText("Gender");
        genderSet.setStyle(fieldStyle);

        Label name = new Label("Name: ");
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

        //limit bij tussenvoegsel/prefix
        prefixInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (prefixInput.getText().length() > PREFIXLIMIT) {
                    String s = prefixInput.getText().substring(0, PREFIXLIMIT);
                    prefixInput.setText(s);
                }
            }
        });

        Label address = new Label("Address: ");

        TextField streetInput = new TextField();
        streetInput.setPromptText("Street");
        streetInput.setStyle(fieldStyle);

        TextField hnumberInput = new TextField();
        hnumberInput.setPromptText("Housenr.");
        hnumberInput.setStyle(fieldStyle);
        hnumberInput.setMaxWidth(90);

        hnumberInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (hnumberInput.getText().length() > HNUMBERLIMIT) {
                    String s = hnumberInput.getText().substring(0, HNUMBERLIMIT);
                    hnumberInput.setText(s);
                }
            }
        });

        Label place = new Label("Town/Place: ");
        TextField placeInput = new TextField();
        placeInput.setPromptText("Town/Place");
        placeInput.setStyle(fieldStyle);

        Label zipcode = new Label("Zip-code: ");
        TextField zipcodeInput = new TextField();
        zipcodeInput.setMaxWidth(100);
        zipcodeInput.setPromptText("Zipcode");
        zipcodeInput.setStyle(fieldStyle);

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
        ComboBox countryList = new ComboBox(FXCollections.observableArrayList("Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", 
                "Bahamas", "Barbados", "Belarus", "Belgium", "Belize", "Bosnia and Herzegovina",
                "Brazil", "Bulgaria", "Canada", "Cayman Islands", "Chile", "China", "Colombia", "Costa Rica",
                "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Ecuador", "Egypt",
                "Estonia", "Finland", "France", "Georgia", "Germany", "Greece", "Hong Kong", "Hungary", "Iceland",
                "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Japan", "Kazakhstan", "Latvia",
                "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia", "Malta", "Mexico", "Moldova",
                "Monaco", "Morocco", "Netherlands", "Netherlands Antilles", "New Zealand", "Norway", "Poland",
                "Portugal", "Qatar", "Romania", "Russia", "San Marino", "Saudi Arabia", "Serbia and Montenegro",
                "Slovakia", "Slovenia", "Spain", "South Korea", "Suriname", "Sweden", "Switzerland", "Tunisia",
                "Turkey", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay"));
        countryList.setValue("Netherlands");
        countryList.setPromptText("Country");
        countryList.setStyle(fieldStyle);

        Label contactDetails = new Label("Contact Details: ");

        TextField phonenrInput = new TextField();
        phonenrInput.setPromptText("Phonenumber");
        phonenrInput.setStyle(fieldStyle);

        TextField emailInput = new TextField();
        emailInput.setPromptText("E-mail (optional)");
        emailInput.setStyle(fieldStyle);
        emailInput.setMaxWidth(400);

        phonenrInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (phonenrInput.getText().length() > PHONELIMIT) {
                    String s = phonenrInput.getText().substring(0, PHONELIMIT);
                    phonenrInput.setText(s);
                }
            }
        });

        Label bagLabel = new Label("Label number: ");
        TextField labelInput = new TextField();
        labelInput.setPromptText("Label number");
        labelInput.setStyle(fieldStyle);
        labelInput.setMaxWidth(120);

        labelInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (labelInput.getText().length() > LABELLIMIT) {
                    String s = labelInput.getText().substring(0, LABELLIMIT);
                    labelInput.setText(s);
                }
            }
        });

        Label flightNr = new Label("Flight number: ");
        TextField flightInput = new TextField("CND");
        flightInput.setPromptText("Flight number");
        flightInput.setStyle(fieldStyle);
        flightInput.setMaxWidth(100);

        flightInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (flightInput.getText().length() > FLIGHTLIMIT) {
                    String s = flightInput.getText().substring(0, FLIGHTLIMIT);
                    flightInput.setText(s);
                }
            }
        });

        Label bagType = new Label("Luggage Type: ");
        ComboBox typeInput = new ComboBox(FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase",
                "Duffel Bag", "Water Container", "Other"));
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle(fieldStyle);

        Label brandName = new Label("Brand name: ");
        ComboBox brandList = new ComboBox(FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak",
                "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        brandList.setPromptText("Brand name");
        brandList.setStyle(fieldStyle);

        Label primaryColor = new Label("Primary Color: ");
        ComboBox priColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver",
                "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle(fieldStyle);

        Label secondaryColor = new Label("Secondary Color (optional): ");
        ComboBox secColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red",
                "Silver", "Grey", "Green", "Yellow", "Purple"));
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle("-fx-base:white;");
        secColorList.setStyle(fieldStyle);

        Label birthDate = new Label("Date of Birth: ");
        DatePicker datePick = new DatePicker();
        datePick.setValue(LocalDate.now().minusYears(18));
        datePick.setShowWeekNumbers(false);

        Label dateSet = new Label("(MM-DD-YYYY)");
        dateSet.setTextFill(Color.web("#D81E05"));
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("Date of Birth");
        datePick.setStyle(fieldStyle);

        Label moreInfo = new Label("Further Luggage Information: ");
        TextArea infoInput = new TextArea();
        infoInput.setPromptText("Further Luggage Information... (optional)");
        infoInput.setMaxWidth(240);
        infoInput.setMaxHeight(150);
        infoInput.setStyle("");

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
        this.add(blank, 8, 1);

        //onderdelen toevoegen
        this.add(hbox, 0, 0, 16, 1);
        this.add(titleBox, 1, 1, 18, 1);
        this.add(title, 1, 1, 1, 1);
        this.add(submit, 8, 17);
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
        this.add(separator1, 5, 2, 10, 15);
        this.add(luggageInfo, 7, 4);
        this.add(bagLabel, 7, 5);
        this.add(labelInput, 9, 5);
        this.add(labelCheck, 9, 5, 1, 3);
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

        this.setStyle("-fx-background-color: white;"
                + "-fx-font-family: Open Sans;"
                + "-fx-fill: red");

        labelCheck.setOnAction((ActionEvent e) -> {
            checkLabel(primaryStage, labelInput.getText());
        });

        submit.setOnAction((ActionEvent e) -> {
            PreparedStatement pst2 = null;
            try {

                if (labelInput.getText().isEmpty() || typeInput.getSelectionModel().isEmpty()
                        || priColorList.getSelectionModel().isEmpty() || brandList.getSelectionModel().isEmpty() || flightInput.getText().isEmpty()
                        || SurnameInput.getText().isEmpty() || NameInput.getText().isEmpty() || datePick.getEditor().getText().isEmpty()
                        || streetInput.getText().isEmpty() || hnumberInput.getText().isEmpty() || placeInput.getText().isEmpty() 
                        || zipcodeInput.getText().isEmpty() || phonenrInput.getText().isEmpty()) {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Corendon - Luggage");
                    alert.setHeaderText(null);
                    alert.setContentText("Some information is not filled in, please try again.");
                    alert.showAndWait();
                    System.out.println("Some information is not filled in");
                } else {

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
                    pst2.setString(5, ((TextField) datePick.getEditor()).getText());
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

            } catch (Exception e1) {
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
        DbManager dbManager = new DbManager();

        //TableView
        final TableView<LuggageRecord2> tableView = dbManager.createLuggageTable();

        luggageData = dbManager.getLuggageListFromDB();
        tableView.setItems(luggageData);

        //prompt
        final Stage checkPopup = new Stage();
        checkPopup.initModality(Modality.APPLICATION_MODAL);
        checkPopup.initOwner(primaryStage);
        
        HBox prompt = new HBox(20);
        VBox controls = new VBox(20);
        controls.setPadding(new Insets(5, 5, 5, 5));
        
        solve.setMinSize(90, 30);
        solve.setStyle("-fx-base:#56ad3e;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-font-size: 20; "
                + "-fx-font-weight: bold;");
        solve.setTextFill(Color.web("#ffffff"));
        solve.setText("Solve");
        
        cancel.setMinSize(70, 20);
        cancel.setStyle("-fx-base:white;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-font-size: 12;");
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
