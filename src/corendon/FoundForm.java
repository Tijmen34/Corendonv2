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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.control.TableView;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Zouhar Alladien
 */
public class FoundForm extends GridPane {
    
    //private DbManager dbManager;
    private ObservableList<LuggageRecord2> luggageData
    = FXCollections.observableArrayList();
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn;
    
    //logo rechtsboven
    private Image corLogo = new Image("Corendon.png");
    private ImageView logo = new ImageView();
    
    //hbox voor logo
    private HBox hbox = new HBox();
    
    //CONSTANTE voor styling
    //border formulier
    final String fieldStyle = "-fx-border-width: 1;\n"
            + "-fx-border-radius: 5;\n"
            + "-fx-border-color: #cccccc;\n"
            + "-fx-background-color: #ffffff;"
            + "-fx-text-inner-color: #555555;";
    //buttonstyle "submit, checklabel"
    final String buttonStyle = "-fx-font: 20px UniSansBoldItalic;"
            + "-fx-base:#56ad3e;"
            + "-fx-border-color:transparent;"
            + "-fx-focus-color: transparent"
            + ";-fx-faint-focus-color: transparent;";

    private Stage primaryStage;


    //button voor labelcheck
    private Button solve = new Button("Solve");
    private Button cancel = new Button("Cancel");
    private final int IATALIMIT = 3;
    
    
    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        CheckConnection();
        //Hbox posistie
        hbox.setStyle("-fx-background-color: white;");
        hbox.setSpacing(0);
        hbox.setAlignment(Pos.TOP_LEFT);
        logo.setImage(corLogo);
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        logo.setCache(true);
        hbox.getChildren().addAll(logo);

        //Titel Formulier
        Label title = new Label("\tForm Luggage Found");
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-font: 20px UniSansSemiBold");
        
        //rode titelbalk
        Rectangle titleBox = new Rectangle();
        titleBox.setWidth(2000);
        titleBox.setHeight(50);
        titleBox.setFill(Color.web("#D81E05"));
        
        
        // Submit knop
        Button submit = new Button("Submit");
        submit.setStyle(buttonStyle);
        submit.setTextFill(Color.web("#ffffff"));

        //Check Label knop
        Button labelCheck = new Button("Check");
        labelCheck.setStyle("-fx-base:#56ad3e;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-font-size: 14;"
                + "-fx-font-weight: bold");
        labelCheck.setTextFill(Color.web("#ffffff"));
        labelCheck.setMaxWidth(120);

        // Informatie plek van invullen
        Label iata = new Label("Airport IATA: ");
        TextField iataSearch = new TextField();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        iataSearch.setStyle(fieldStyle);
        
        //limiet 3 characters
        iataSearch.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (iataSearch.getText().length() > IATALIMIT) {
                    String s = iataSearch.getText().substring(0, IATALIMIT);
                    iataSearch.setText(s);
                }
            }
        });
        
        //kopje "labelinformation"
        Label luggageLabelInfo = new Label("Label information");
        luggageLabelInfo.setTextFill(Color.web("#00bce2"));
        luggageLabelInfo.setStyle("-fx-font: 18px UniSansRegular");
        //kopje "luggage information"
        Label luggageInfo = new Label("Luggage Information");
        luggageInfo.setTextFill(Color.web("#00bce2"));
        luggageInfo.setStyle("-fx-font: 18px UniSansRegular");

        //lijn midden van formulier
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);

        // Algemene informatie (label + textfield/combobox) 
        Label bagLabel = new Label("Label number: ");
        TextField labelInput = new TextField();
        labelInput.setPromptText("Label number");
        labelInput.setStyle(fieldStyle);
        
        
        Label flightNr = new Label("Flight number: ");
        TextField flightInput = new TextField("CND");
        flightInput.setPromptText("Flight number");
        flightInput.setMaxWidth(400);
        flightInput.setStyle(fieldStyle);
        
        Label destination = new Label("Destination IATA: ");
        TextField destinationInput = new TextField();
        destinationInput.setPromptText("IATA");
        destinationInput.setStyle(fieldStyle);
        destinationInput.setMaxWidth(55);
        
        //limiet 3 characters
        destinationInput.textProperty().addListener(new ChangeListener<String>() {
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (destinationInput.getText().length() > IATALIMIT) {
                    String s = destinationInput.getText().substring(0, IATALIMIT);
                    destinationInput.setText(s);
                }
            }
        });

        //baggage informatie (label + textfield/combobox)
        Label bagType = new Label("Luggage Type: ");
        ComboBox typeInput = new ComboBox(FXCollections.observableArrayList("Carry-on", "Wheeled Luggage",
                "Suitcase", "Duffel Bag", "Water Container", "Other"));
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle("-fx-base:white");
        typeInput.setStyle(fieldStyle);

        Label brandName = new Label("Brand name: ");
        ComboBox brandList = new ComboBox(FXCollections.observableArrayList("Samsonite", "American Tourister",
                "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        brandList.setPromptText("Brand name");
        brandList.setStyle("-fx-base:white");
        brandList.setStyle(fieldStyle);

        Label primaryColor = new Label("Primary Color: ");
        ComboBox priColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue",
                "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle("-fx-base:white");
        priColorList.setStyle(fieldStyle);

        Label secondaryColor = new Label("Secondary Color: ");
        ComboBox secColorList = new ComboBox(FXCollections.observableArrayList("Black", "White", "Blue", "Red",
                "Silver", "Grey", "Green", "Yellow", "Purple"));
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle(fieldStyle);

        Label moreInfo = new Label("Further Luggage Information: ");
        TextArea infoInput = new TextArea();
        infoInput.setPromptText("Further Luggage Information...            ");
        infoInput.setMaxWidth(240);

        //layout gridpane
        this.setHgap(15);
        this.setVgap(15);
        this.setPadding(new Insets(30, 30, 30, 30));

        //ONDERDELEN TOEVOEGEN
        this.add(hbox, 0, 0, 17, 1);
        this.add(titleBox, 1, 1, 18, 1);
        this.add(title, 1, 1, 1, 1);

        //IATA
        this.add(iata, 1, 4);
        this.add(iataSearch, 3, 4);

        //BAGAGELABEL
        this.add(luggageLabelInfo, 1, 6, 2, 1);
        this.add(bagLabel, 1, 7);
        this.add(labelInput, 3, 7);
        this.add(labelCheck, 3, 8);
        this.add(flightNr, 1, 9);
        this.add(flightInput, 3, 9);
        this.add(destination, 1, 10);
        this.add(destinationInput, 3, 10);
        this.add(submit, 8, 15);

        this.add(separator1, 5, 2, 10, 18);

        //BAGAGEINFO
        this.add(luggageInfo, 7, 4);
        this.add(bagType, 7, 5);
        this.add(typeInput, 9, 5);
        this.add(brandName, 7, 6);
        this.add(brandList, 9, 6);
        this.add(primaryColor, 7, 7);
        this.add(priColorList, 9, 7);
        this.add(secondaryColor, 9, 8);
        this.add(secColorList, 9, 8);
        this.add(moreInfo, 7, 9);
        this.add(infoInput, 9, 9, 1, 4);

        this.setStyle("-fx-background-color: white");
        
        //voert dit uit zodra "checklabel" is aan geklikt
        labelCheck.setOnAction((ActionEvent e) -> {
            checkLabel(primaryStage, labelInput.getText());
        });

        //zodra er op submit wordt geklikt
        submit.setOnAction((ActionEvent e) -> {
            
            try {
                //checkt of deze label zijn ingevuld, anders melding dat er nog wat moet worden ingevuld
                if (labelInput.getText().isEmpty() || typeInput.getSelectionModel().isEmpty() || priColorList.getSelectionModel().isEmpty()
                        || brandList.getSelectionModel().isEmpty() || flightInput.getText().isEmpty() || destinationInput.getText().isEmpty()) {
                    //alert niet alles is ingevuld
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("Corendon - Luggage");
                    alert.setHeaderText(null);
                    alert.setContentText("Some information is not filled in, please try again.");
                    alert.showAndWait();
                    System.out.println("Some information is not filled in");
                } else {
                    //alert alles ingevuld
                    Alert alert1 = new Alert(AlertType.INFORMATION);
                    alert1.setTitle("Corendon - Luggage");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Information successfully submitted");
                    alert1.showAndWait();
                    System.out.println("Information submitted.");
                    //QUERY vult bagage in database onder "bagage"
                    String query = "INSERT INTO bagage"
                            + "(labelnr, vlucht, iata, lugType, merk, Prikleur, SecKleur, extra_info, status, datum_bevestiging, destination) VALUES"
                            + "(?,?,?,?,?,?,?,?,'found',NOW(),?)";

                    //ingevulde text wordt hier opgenomen
                    pst = conn.prepareStatement(query);
                    pst.setString(1, labelInput.getText());
                    pst.setString(2, flightInput.getText());
                    pst.setString(3, iataSearch.getText());
                    pst.setString(5, (String) brandList.getSelectionModel().getSelectedItem());
                    pst.setString(6, (String) priColorList.getSelectionModel().getSelectedItem());
                    pst.setString(7, (String) secColorList.getSelectionModel().getSelectedItem());
                    pst.setString(4, (String) typeInput.getSelectionModel().getSelectedItem());
                    pst.setString(8, infoInput.getText());
                    pst.setString(9, destinationInput.getText());

                    pst.executeUpdate();

                }
                //alert als er iets mis is met database
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
    //checkt connectie van database
    public void CheckConnection() {
        conn = Sql.DbConnector();
        if (conn == null) {
            System.out.println("Connection lost.");
            System.exit(1);
        }
    }
    //methode voor checklabel
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
        
        //knop style "Solve"
        solve.setMinSize(90, 30);
        solve.setStyle("-fx-base:#56ad3e;"
                + "-fx-border-color:transparent;"
                + "-fx-focus-color: transparent;"
                + "-fx-faint-focus-color: transparent;"
                + "-fx-font-size: 20; "
                + "-fx-font-weight: bold;");
        solve.setTextFill(Color.web("#ffffff"));
        solve.setText("Solve");
        //knop style "Cancel"
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
        
        //Als op Solve wordt geklikt... (1/2)
        solve.setOnAction((ActionEvent e) -> {
            solveFromPrompt(tableView);
        });
        //sluit checklabel af 
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
                // ...zal de geselecteerde bagage van lost naar solved veranderd worden. (2/2)
                String id = luggageData.get(tableView.getSelectionModel().getSelectedIndex()).getLostId();
                String SQL = "UPDATE bagage SET status = 'solved' WHERE lost_id = " + "'" + id + "'";
                System.out.println(SQL);
                conn.createStatement().executeUpdate(SQL);

                luggageData.remove(tableView.getSelectionModel().getSelectedIndex());
            // bij fout
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }

        }
    }

}
