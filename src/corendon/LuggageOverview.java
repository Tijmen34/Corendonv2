/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.lang.Exception;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 * Deze class regeld te tab met het bagage overzicht.
 * 
 * @author iS109-3
 */
public class LuggageOverview extends BorderPane {

    private Stage primaryStage;
    private DbManager dbManager;

    private ObservableList<LuggageRecord2> data          //In deze lijst komt de data binnen uit de db
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> tableData     //deze lijst is wat de grote tabel laat zien
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> stickyData    //deze lijst is wat de bovenste tabel laat zien
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> searchResults //deze lijst bevat wat er in de grote tabel komt als zoekresultaat
            = FXCollections.observableArrayList();

    private TableView<LuggageRecord2> tableViewSticky3; //grote tabel
    private TableView<LuggageRecord2> tableView4;       //kleine tabel voor de selectie

    private final VBox controlBox = new VBox();
    private final ScrollPane tableScroll = new ScrollPane();
    private final StackPane stickyBox = new StackPane();
    private final HBox topBar = new HBox();
    private final HBox topBar2 = new HBox();
    private final BorderPane border1 = new BorderPane();

    private final Button selToStickyBtn = new Button("Select");
    private final Button selUnStickyBtn = new Button("Deselect");
    private final Button stickyMatchBtn = new Button("Solve");

    private final Button editRecord = new Button("Edit");

    private final Button back = new Button("Back");
    private final Button refresh = new Button("Refresh");
    private final Button searchButton = new Button("Search");
    private final Label space = new Label(" ");
    private final Button delete = new Button("Delete");
    private final Button deleteExpired = new Button("Delete expired");
    private TextField searchBar = new TextField();
    private final Label tableStatus = new Label("Overview:");

    //deze buttons worden pas geinstanciëerd als de popup tevoorschijn komt
    private Button cancelEdit;
    private Button confirmEdit;
    private Button resetEdit;
    
    private boolean isShowingSearch; //true als zoekresultaten worden laten zien

    //in plaats van een constructor hebben wij een andere methode die we meteen aanroepen
    //na het instanciëren van deze class. De constructor van BorderPane zal wel worden
    //uitgevoerd
    public void initScreen(Stage primaryStage) {
        this.selToStickyBtn.setPrefSize(100, 20);
        this.selUnStickyBtn.setPrefSize(100, 20);
        this.stickyMatchBtn.setPrefSize(100, 20);
        
        this.primaryStage = primaryStage;
        
        //haal bagage records uit de db en stop ze in de lijst die de tabel gaat vullen
        dbManager = new DbManager();
        this.data = dbManager.getLuggageListFromDB();
        for (int i = 0; i < data.size(); i++) {
            tableData.add(data.get(i));
        }
        
        //roep methodes aan om de volledig klaargemaakte tabellen te krijgen
        tableView4 = dbManager.createLuggageTable();
        tableViewSticky3 = dbManager.createLuggageTable();
        isShowingSearch = false;

        //zorg dat de juiste panes en boxes elkaar vasthouden
        this.setTop(topBar);
        this.setRight(controlBox);
        this.setCenter(border1);
        border1.setTop(stickyBox);
        stickyBox.getChildren().add(tableViewSticky3);
        border1.setCenter(tableView4);

        //-------------------------------------------
        //balk met controls voor tabel rechts
        controlBox.getChildren().addAll(selUnStickyBtn, stickyMatchBtn, selToStickyBtn, space, editRecord, deleteExpired, delete);
        controlBox.setSpacing(50);

        //-------------------------------------------
        //Rode balk bovenin het scherm
        searchBar = new TextField();
        Image corLogo = new Image("Corendon.png");
        ImageView logo = new ImageView();
        logo.setImage(corLogo);
        logo.setFitWidth(300);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        topBar.getChildren().addAll(topBar2, tableStatus, searchBar, searchButton, refresh);
        searchButton.setMinSize(20, 25);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar2.getChildren().addAll(logo);
        topBar2.setAlignment(Pos.CENTER_RIGHT);
        // ------------------------------------------

        tableView4.setMinSize(1000, (22 * 24) + 26);
        tableView4.setMaxSize(1000, (22 * 24) + 26);
        tableView4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //-------------------------------------------
        //Sticky Tabel
        tableViewSticky3.setMinSize(1000, 24 + 26);
        tableViewSticky3.setPrefSize(1000, 24 + 26);
        tableViewSticky3.setMaxWidth(1000);
        tableViewSticky3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //-------------------------------------------
        //tabellen vullen
        tableViewSticky3.setItems(this.stickyData);
        tableView4.setItems(this.tableData);
        
        //--------------------------------------------

        //Buttons functioneel
        selToStickyBtn.setOnAction((ActionEvent e) -> {
            if (isShowingSearch == false) {
                stickyData.add(tableData.get(tableView4.getSelectionModel().getSelectedIndex()));
                tableData.remove(tableData.get(tableView4.getSelectionModel().getSelectedIndex()));
                stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
                tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);
            } else {
                stickyData.add(searchResults.get(tableView4.getSelectionModel().getSelectedIndex()));
                searchResults.remove(searchResults.get(tableView4.getSelectionModel().getSelectedIndex()));
                stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
                tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);
            }
        });

        selUnStickyBtn.setOnAction((ActionEvent e) -> {
            if (isShowingSearch == false) {
                tableData.add(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
                stickyData.remove(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
                stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
                tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);
            } else {
                searchResults.add(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
                tableData.add(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
                stickyData.remove(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
                stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
                tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);
            }
        });

        stickyMatchBtn.setOnAction((ActionEvent e) -> {
            solveStickyItems();
        });

        searchButton.setOnAction((ActionEvent e) -> {
            isShowingSearch = true;
            searchItems();
            tableStatus.setText("Search Results:");
            topBar.getChildren().add(back);
            topBar.getChildren().add(delete);
        });

        back.setOnAction((ActionEvent e) -> {
            isShowingSearch = false;
            tableView4.setItems(tableData);
            tableData.removeAll(stickyData);
            tableStatus.setText("Overview:");
            topBar.getChildren().removeAll(back, delete);
            controlBox.getChildren().add(delete);
        });

        delete.setOnAction((ActionEvent e) -> {
            deleteLuggage(false);
        });

        deleteExpired.setOnAction((ActionEvent e) -> {
            deleteLuggage(true);
        });
        

        refresh.setOnAction((ActionEvent e) -> {
            for (int i = 0; i < tableView4.getItems().size(); i++) {
                tableView4.getItems().clear();
            }
            updateData();

        });

        editRecord.setOnAction((ActionEvent e) -> {
            editLuggageRecord(this.primaryStage, tableView4.getSelectionModel().getSelectedItem());
        });
    }
    
    //methode om te zoeken in de tabel en de resultaten te laten zien in de tabel
    public void searchItems() {
        searchResults.clear();
        String keyword = searchBar.getText();
        for (LuggageRecord2 record : tableData) {
            SimpleStringProperty[] properties = record.toArray();
            boolean relevance = false;
            for (int i = 0; i < properties.length; i++) {
                if (keyword.equals(properties[i].getValueSafe())) {
                    relevance = true;
                }
                System.out.println(properties[i].toString());
                System.out.println(relevance);
            }
            if (relevance == true) {
                searchResults.add(record);
            }
        }
        tableView4.setItems(searchResults);
        System.out.println(searchResults.toString());
    }
    
    //methode om 2 items in de bovenste tabel aan te merken als match,
    //om daarna 1 item terug te stoppen met de stauts 'solved'
    public void solveStickyItems() {
        if ((this.stickyData.size() == 2)
                && ((stickyData.get(0).getStatus().equals("lost") && stickyData.get(1).getStatus().equals("found"))
                || (stickyData.get(0).getStatus().equals("found") && stickyData.get(1).getStatus().equals("lost")))) {
            try (Connection conn = Sql.DbConnector();) {
                LuggageRecord2 found;
                LuggageRecord2 lost;
                if (stickyData.get(0).getStatus().equals("found")) {
                    found = stickyData.get(0);
                    lost = stickyData.get(1);
                } else {
                    found = stickyData.get(1);
                    lost = stickyData.get(0);
                }

                PreparedStatement pst;
                String SQL = "INSERT INTO bagage"
                        + "(lost_id, labelnr, vlucht, iata, lugType, merk, Prikleur, SecKleur, extra_info, status, datum_bevestiging) VALUES"
                        + "(?,?,?,?,?,?,?,?,?,?,'solved',NOW())";
                pst = conn.prepareStatement(SQL);
                pst.setString(1, found.getLostId());
                pst.setString(2, found.getLabelNr());
                pst.setString(3, lost.getFlightNr());
                pst.setString(4, found.getIata());
                pst.setString(5, found.getType());
                pst.setString(6, found.getBrandName());
                pst.setString(7, found.getPrimaryColor());
                pst.setString(8, found.getSecondaryColor());
                pst.setString(9, found.getInfo());
                pst.setString(10, lost.getCustomerId());

                System.out.println(SQL);
                conn.createStatement().executeUpdate(SQL);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error on Building Data");
            }
            this.data.clear();
            this.tableData.clear();
            this.stickyData.clear();
            this.data = dbManager.getLuggageListFromDB();
            for (int i = 0; i < data.size(); i++) {
                tableData.add(data.get(i));
            }
        } else {
            System.out.println("Neem 1 gevonden en 1 vermist bagagestuk");
        }
    }

    //methode voor het deleten van bagage, als de boolean true is zal alle
    //bagage die delivered is en ouder dan een jaar is verwijderd worden.
    //Anders: verwijder alleen de selectie
    public void deleteLuggage(boolean deleteAllExpired) {

        int selectedIndex = tableView4.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete bagage?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                PreparedStatement prepS = null;

                try (Connection conn = Sql.DbConnector();) {
                    String query;
                    if (deleteAllExpired == false) {
                        query = "DELETE from bagage WHERE lost_id = ?";
                        prepS = conn.prepareStatement(query);
                        prepS.setString(1, tableView4.getSelectionModel().getSelectedItem().getLostId());
                        tableView4.getItems().remove(selectedIndex);
                    } else {
                        query = "DELETE form bagage WHERE status = 'Solved' AND DATE(date_time) < DATE(NOW() - INTERVAL 1 YEAR)";
                        prepS = conn.prepareStatement(query);
                        this.data.clear();
                        this.tableData.clear();
                        this.data = dbManager.getLuggageListFromDB();
                        for (int i = 0; i < data.size(); i++) {
                            tableData.add(data.get(i));
                        }
                    }

                    prepS.executeUpdate();

                } catch (Exception e1) {
                    System.out.println("SQL ERROR");
                    System.err.println(e1);
                }
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a luggage item in the table.");

            alert.showAndWait();
        }
    }
    
    //methode die een popup tevrooschijn haalt waarin de details van een 
    //bagage record aangepast kunnen worden.
    public void editLuggageRecord(Stage primaryStage, LuggageRecord2 recordToEdit) {

        //niewe popup, de rest van de app bevriest.
        //LuggageRecord2 recordToEdit = tableView4.getSelectionModel().getSelectedItem();
        final Stage checkPopup = new Stage();
        checkPopup.initModality(Modality.APPLICATION_MODAL);
        checkPopup.initOwner(primaryStage);
        GridPane form = new GridPane();
        this.cancelEdit = new Button("Cancel");
        this.confirmEdit = new Button("Ok");
        this.resetEdit = new Button("Reset");

        Label title = new Label("Edit record");
        Label labelnr = new Label("Label nr");
        Label vlucht = new Label("Flight nr");
        Label iata = new Label("IATA");
        Label lugType = new Label("Type");
        Label merk = new Label("Brand");
        Label prikleur = new Label("Primary color");
        Label seckleur = new Label("Secondary color");
        Label extraInfo = new Label("Extra info");
        Label status = new Label("Status");
        Label datumBevestiging = new Label("Date and Time");

        TextField labelnrField = new TextField(recordToEdit.getLabelNr());
        TextField vluchtField = new TextField(recordToEdit.getFlightNr());
        TextField iataField = new TextField(recordToEdit.getIata());
        TextField lugTypeField = new TextField(recordToEdit.getType());
        TextField merkField = new TextField(recordToEdit.getBrandName());
        TextField prikleurField = new TextField(recordToEdit.getPrimaryColor());
        TextField seckleurField = new TextField(recordToEdit.getSecondaryColor());
        TextField extraInfoField = new TextField(recordToEdit.getInfo());
        TextField statusField = new TextField(recordToEdit.getStatus());
        TextField datumBevestigingField = new TextField(recordToEdit.getDate());

        form.add(title, 1, 1);

        form.add(labelnr, 1, 2);
        form.add(labelnrField, 2, 2, 2, 1);
        form.add(vlucht, 1, 3);
        form.add(vluchtField, 2, 3, 2, 1);
        form.add(iata, 1, 4);
        form.add(iataField, 2, 4, 2, 1);
        form.add(lugType, 1, 5);
        form.add(lugTypeField, 2, 5, 2, 1);
        form.add(merk, 1, 6);
        form.add(merkField, 2, 6, 2, 1);
        form.add(prikleur, 1, 7);
        form.add(prikleurField, 2, 7, 2, 1);
        form.add(seckleur, 1, 8);
        form.add(seckleurField, 2, 8, 2, 1);
        form.add(extraInfo, 1, 9);
        form.add(extraInfoField, 2, 9, 2, 1);
        form.add(status, 1, 10);
        form.add(statusField, 2, 10, 2, 1);
        form.add(datumBevestiging, 1, 11);
        form.add(datumBevestigingField, 2, 11, 2, 1);

        form.add(confirmEdit, 1, 12);
        form.add(resetEdit, 2, 12);

        form.add(cancelEdit, 3, 12);

        // OK button verzend de update
        confirmEdit.setOnAction((ActionEvent e) -> {
            try (Connection conn = Sql.DbConnector();) {

                PreparedStatement pst;
                String SQL = "UPDATE bagage "
                        + "SET labelnr= ?, vlucht= ?, iata= ?, lugType= ?, merk= ?, Prikleur= ?, SecKleur= ?, "
                        + "extra_info= ?, status= ?, datum_bevestiging= ? "
                        + "WHERE lost_id= ?";

                pst = conn.prepareStatement(SQL);
                pst.setString(1, labelnrField.getText());
                pst.setString(2, vluchtField.getText());
                pst.setString(3, iataField.getText());
                pst.setString(4, lugTypeField.getText());
                pst.setString(5, merkField.getText());
                pst.setString(6, prikleurField.getText());
                pst.setString(7, seckleurField.getText());
                pst.setString(8, extraInfoField.getText());
                pst.setString(9, statusField.getText());
                pst.setString(10, datumBevestigingField.getText());
                pst.setString(11, recordToEdit.getLostId());

                pst.executeUpdate();
                //conn.createStatement().executeUpdate(SQL);
                System.out.println(SQL);
                System.out.println("test " + recordToEdit.getDate());
            } catch (Exception e2) {
                e2.printStackTrace();
                System.out.println("Error on Building Data");
            }
            this.data.clear();
            this.tableData.clear();
            this.data = dbManager.getLuggageListFromDB();
            for (int i = 0; i < data.size(); i++) {
                tableData.add(data.get(i));
            }
            checkPopup.close();

        });
        //reset button zet de textvelden weer op default
        resetEdit.setOnAction((ActionEvent e) -> {
            labelnrField.setText(recordToEdit.getLabelNr());
            vluchtField.setText(recordToEdit.getFlightNr());
            iataField.setText(recordToEdit.getIata());
            lugTypeField.setText(recordToEdit.getType());
            merkField.setText(recordToEdit.getBrandName());
            prikleurField.setText(recordToEdit.getPrimaryColor());
            seckleurField.setText(recordToEdit.getSecondaryColor());
            extraInfoField.setText(recordToEdit.getInfo());
            statusField.setText(recordToEdit.getStatus());
            datumBevestigingField.setText(recordToEdit.getDate());
        });
        //cancel button sluit de popup
        cancelEdit.setOnAction((ActionEvent e) -> {
            checkPopup.close();

        });

        form.setPadding(new Insets(0, 0, 0, 0));
        form.getChildren().addAll();

        Scene dialogScene = new Scene(form, 250, 300);
        checkPopup.setScene(dialogScene);
        checkPopup.show();
    }
    
        public void updateData() {

        data = dbManager.getLuggageListFromDB();
        for (int i = 0; i < this.data.size(); i++) {
            this.tableData.add(this.data.get(i));
        }
        tableView4.setItems(this.tableData);

    }

}
