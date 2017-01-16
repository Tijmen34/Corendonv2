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
import javafx.scene.shape.Rectangle;
/**
 *
 * @author Zouhar Alladien
 */
public class FoundForm extends GridPane{
       ResultSet rs = null;
       PreparedStatement pst = null; 
       Connection conn;
       Statement stmt;
       
       final String fieldStyle = "-fx-border-width: 1;\n" +
            "-fx-border-radius: 5;\n" +
            "-fx-border-color: #cccccc;\n" +
            "-fx-background-color: #ffffff;"+
            "-fx-text-inner-color: #555555;";
      
      final String buttonStyle ="-fx-font: 20px UniSansBoldItalic;"
              + "-fx-base:#56ad3e;"
              + "-fx-border-color:transparent;"
              + "-fx-focus-color: transparent"
              + ";-fx-faint-focus-color: transparent;";
       
       private Stage primaryStage; 
       private ObservableList<LuggageRecord2> luggageData
            = FXCollections.observableArrayList();

        private Button solve = new Button("Solve");
        private Button cancel = new Button("Cancel");
    
    // Constructor overriden kan niet, en een eigen constructor maken ipv de originele
    // maakt meer kapot dan je lief is, dus we schrijven een nieuwe methode om alle
    // elementen meteen aan het scherm toe te voegen.
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
       
      
        //Formulier
        
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
        Label title = new Label("\tForm Luggage Found");
        //title.setFont(UniSans);
        title.setTextFill(Color.web("#FFFFFF"));
        title.setStyle("-fx-font: 20px UniSansSemiBold");
        
        Rectangle titleBox = new Rectangle();
        
        titleBox.setWidth(2000);
        titleBox.setHeight(50);
        titleBox.setFill(Color.web("#D81E05"));
        
        Label luggageLabelInfo = new Label("Label information");
        //PassInfo.setFont(UniSansItalicsmall);
        luggageLabelInfo.setTextFill(Color.web("#00bce2"));
        luggageLabelInfo.setStyle("-fx-font: 18px UniSansRegular");
        
        Label luggageInfo = new Label("Luggage Information");
        //luggageInfo.setFont(UniSansItalicsmall);
        luggageInfo.setTextFill(Color.web("#00bce2"));
        luggageInfo.setStyle("-fx-font: 18px UniSansRegular");
        
        
        //Separator separator = new Separator();
        
        Separator separator1 = new Separator();
        separator1.setOrientation(Orientation.VERTICAL);
        
        
        
        
        
        
        // Submit knop
        Button next = new Button();
        //next.setFont(UniSansItalicbig);
        next.setStyle(buttonStyle);
        next.setTextFill(Color.web("#ffffff"));
       
        next.setText("Submit");
        
         //Check Label knop
        Button labelCheck = new Button("check");
        //next.setFont(UniSansItalicbig);
        labelCheck.setStyle("-fx-base:#56ad3e;-fx-border-color:transparent;-fx-focus-color: transparent;-fx-faint-focus-color: transparent;-fx-font-size: 14; -fx-font-weight: bold");
        labelCheck.setTextFill(Color.web("#ffffff"));
        labelCheck.setText("Check");
        labelCheck.setMaxWidth(120);
        
        // Informatie plek van invullen
        Label iata= new Label("Airport IATA: ");
        //iata.setFont(OpenSans);
        TextField iataSearch = new TextField ();
        iataSearch.setPromptText("IATA");
        iataSearch.setMaxWidth(55);
        iataSearch.setStyle(fieldStyle);
        
        
        // Algemene informatie  
        Label date = new Label("Date: ");
        date.setTextFill(Color.web("#333333"));
        DatePicker datePick = new DatePicker();
        datePick.setShowWeekNumbers(false);
        //dateSet.setFont(OpenSans);
        datePick.setStyle("-fx-base:white;");
        datePick.setPromptText("DD-MM-JJJJ");
        datePick.setStyle(fieldStyle);

        
        Label airport= new Label("Airport: ");
        airport.setTextFill(Color.web("#333333"));
        TextField airportInput = new TextField ();
        //airport.setFont(OpenSans);
        airportInput.setPromptText("Airport");
        airportInput.setStyle(fieldStyle);
        
        
        
        Label time= new Label("Time: ");
        time.setTextFill(Color.web("#333333"));
        TextField timeInput = new TextField ();
        //place.setFont(OpenSans);
        timeInput.setPromptText("00:00");
        timeInput.setMaxWidth(90);
        timeInput.setStyle(fieldStyle);

        //Label informatie
        Label bagLabel= new Label("Label number: ");
        bagLabel.setTextFill(Color.web("#333333"));
        TextField labelInput = new TextField ();
        //bagLabel.setFont(OpenSans);
        labelInput.setPromptText("Label number");
        labelInput.setStyle(fieldStyle);
        
        Label flightNr= new Label("Flight number: ");
        flightNr.setTextFill(Color.web("#333333"));
        TextField flightInput = new TextField ("CND");
        //flightNr.setFont(OpenSans);
        flightInput.setPromptText("Flight number");
        flightInput.setMaxWidth(400);
        flightInput.setStyle(fieldStyle);
        
         Label destination= new Label("Destination: ");
        //name.setFont(OpenSans);
        destination.setTextFill(Color.web("#333333"));
        TextField destinationInput = new TextField ();
        destinationInput.setPromptText("Destination");
        destinationInput.setStyle(fieldStyle);
        
//        Label nameTraveler= new Label("Name traveler: ");
//        nameTraveler.setTextFill(Color.web("#333333"));
//        TextField surnameInput = new TextField ();
//        //contactDetails.setFont(OpenSans);
//        surnameInput.setPromptText("Surname");
//        surnameInput.setStyle(fieldStyle);
//        
//        Label firstname = new Label("firstname: ");
//        firstname.setTextFill(Color.web("#333333"));
//        TextField firstNameInput = new TextField ();
//        //email.setFont(OpenSans);
//        firstNameInput.setPromptText("Firstname");
//        firstNameInput.setMaxWidth(400);
//        firstNameInput.setStyle(fieldStyle);
        
        //baggage informatie
        Label bagType= new Label("Luggage Type: ");
        bagType.setTextFill(Color.web("#333333"));
        ComboBox typeInput = new ComboBox (FXCollections.observableArrayList("Carry-on", "Wheeled Luggage", "Suitcase", "Duffel Bag", "Water Container", "Other"));
        //bagLabel.setFont(OpenSans);
        typeInput.setPromptText("Luggage Type");
        typeInput.setStyle("-fx-base:white");
        typeInput.setStyle(fieldStyle);
        
        Label brandName= new Label("Brand name: ");
        brandName.setTextFill(Color.web("#333333"));
        ComboBox brandList = new ComboBox (FXCollections.observableArrayList("Samsonite", "American Tourister", "Princess", "Eastpak", "Delsey", "JanSport", "Pierre Cardin", "Rimowa", "Other", "Brandless", "Unknown"));
        //brandName.setFont(OpenSans);
        brandList.setPromptText("Brand name");
        brandList.setStyle("-fx-base:white");
        brandList.setStyle(fieldStyle);
        
        Label primaryColor= new Label("Primary Color: ");
        primaryColor.setTextFill(Color.web("#333333"));
        ComboBox priColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple", "Multicolor", "Other"));
        //primaryColor.setFont(OpenSans);
        priColorList.setPromptText("Primary Color");
        priColorList.setStyle("-fx-base:white");
        priColorList.setStyle(fieldStyle);
        
        Label secondaryColor= new Label("Secondary Color: ");
        secondaryColor.setTextFill(Color.web("#333333"));
        ComboBox secColorList = new ComboBox (FXCollections.observableArrayList("Black", "White", "Blue", "Red", "Silver", "Grey", "Green", "Yellow", "Purple"));
        //secondaryColor.setFont(OpenSans);
        secColorList.setPromptText("Secondary Color");
        secColorList.setStyle(fieldStyle);
        
        
           
        
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
        this.add(hbox,0,0,16,1);
        this.add(title,1,1,2,1);
        this.add(titleBox,1,1,18,1);
        //this.add(separator, 1, 1);
        this.add(next,8, 15);
        //ALGEMEEN
        
        this.add(airport,1,4);
        this.add(airportInput,2,4);
        this.add(iata, 1, 5);
        this.add(iataSearch, 2,5);
        
        //this.add(date,1,6);
       //this.add(datePick, 2, 6);
       // this.add(time, 1, 7);
        //this.add(timeInput, 2,7);
       
        //BAGAGELABEL
        this.add(luggageLabelInfo, 1, 6, 2, 1);
        this.add(bagLabel, 1, 7);
        this.add(labelInput, 2,7);
        this.add(labelCheck, 2, 7,1,5);
        this.add(flightNr, 1, 8);
        this.add(flightInput, 2,8);
        this.add(destination, 1, 10);
        this.add(destinationInput, 2,10);
//        this.add(nameTraveler, 1, 11);
//        this.add(firstNameInput, 2,11);
//        this.add(surnameInput, 3,11);
        this.add(separator1,5,2, 10,18);
        //BAGAGEINFO
        this.add(luggageInfo, 7, 4);
        this.add(bagType,7,5);
        this.add(typeInput,8,5);
        this.add(brandName,7,6);
        this.add(brandList,8,6);
        this.add(primaryColor,7,7);
        this.add(priColorList,8,7);
        this.add(secondaryColor,7,8);
        this.add(secColorList,8,8);
        this.add(moreInfo,7,9);
        this.add(infoInput,8,9,1,6);
        
        
        
        
       
        this.setStyle("-fx-background-color: white");
       
            labelCheck.setOnAction((ActionEvent e) -> {
                checkLabel(primaryStage, labelInput.getText());
            });
        
              next.setOnAction((ActionEvent e) -> {
            //PreparedStatement pst2 = null;
                try {
                        if(labelInput.getText().isEmpty()|| typeInput.getSelectionModel().isEmpty() || priColorList.getSelectionModel().isEmpty() 
                         ||brandList.getSelectionModel().isEmpty() || flightInput.getText().isEmpty() ||  datePick.getEditor().getText().isEmpty() ||destinationInput.getText().isEmpty() ){               
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
                            //vult tabel bagage
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
                        rs.getString("labelnr"), rs.getString("vlucht"), rs.getString("iata"),
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

