/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import java.lang.Exception;
import java.util.Calendar;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.EventType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

/**
 *
 * @author iS109-3
 */
public class LuggageOverview extends BorderPane {

    private Stage primaryStage;
    private DbManager dbManager;

    private ObservableList<LuggageRecord2> data
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> tableData
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> stickyData
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> searchResults
            = FXCollections.observableArrayList();

    private TableView<LuggageRecord2> tableViewSticky3;
    private TableView<LuggageRecord2> tableView4;

    private VBox controlBox = new VBox();
    private ScrollPane tableScroll = new ScrollPane();
    private StackPane stickyBox = new StackPane();
    private HBox topBar = new HBox();
    private HBox topBar2 = new HBox();
    private BorderPane border1 = new BorderPane();

    private Button selToStickyBtn = new Button("Select");
    private Button selUnStickyBtn = new Button("Deselect");
    private Button stickyMatchBtn = new Button("Solve");

    private Button editRecord = new Button("Ëdit");

    private Button back = new Button("back");
    private Button searchButton = new Button("search");
    private Button delete = new Button("delete");
    private TextField searchBar = new TextField();
    private Label tableStatus = new Label("Overview:");

    private Button cancelEdit;
    private Button confirmEdit;
    private Button resetEdit;

    private boolean isShowingSearch;

    public void initScreen(Stage primaryStage) {
        this.selToStickyBtn.setPrefSize(100, 20);
        this.selUnStickyBtn.setPrefSize(100, 20);
        this.stickyMatchBtn.setPrefSize(100, 20);

        this.primaryStage = primaryStage;
        dbManager = new DbManager();
        this.data = dbManager.getLuggageListFromDB();
        for (int i = 0; i < data.size(); i++) {
            tableData.add(data.get(i));
        }

        tableView4 = dbManager.createLuggageTable();
        tableViewSticky3 = dbManager.createLuggageTable();
        isShowingSearch = false;

        //ScrollPane scroll2 = new ScrollPane();
        /*
        hierarchie:
                      LuggageOverview
        border1,                        topBar
        tableSticky2,scroll2
                      table3
        
         */
        this.setTop(topBar);
        this.setRight(controlBox);
        this.setCenter(border1);
        border1.setTop(stickyBox);
        stickyBox.getChildren().add(tableViewSticky3);
        border1.setCenter(tableView4);

        //-------------------------------------------
        //balk met controls voor tabel rechts
        controlBox.getChildren().addAll(selUnStickyBtn, stickyMatchBtn, selToStickyBtn, editRecord);
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

        topBar.getChildren().addAll(topBar2, tableStatus, searchBar, searchButton);
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
        //--------------------------------------------
        //test record
//        LuggageRecord2 testRecord = new LuggageRecord2("0001", "3R5F2", "MH370",
//                "Suitcase", "jemoeder", "Red", "Black",
//                "NULL", "12324", "Missing", false);
        //-------------------------------------------
        //tabellen vullen
        tableViewSticky3.setItems(this.stickyData);
        tableView4.setItems(this.tableData);
        //stickyData.clear();
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
            topBar.getChildren().removeAll(back);
        });

        delete.setOnAction((ActionEvent e) -> {
            deleteLuggage();
        });

        editRecord.setOnAction((ActionEvent e) -> {
            editLuggageRecord(primaryStage);
        });
    }

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

    public void updateData() {
        stickyData.clear();
        tableData.clear();
        for (LuggageRecord2 record : data) {
            tableData.add(record);
        }
    }

    public void deleteLuggage() {

        int selectedIndex = tableView4.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete bagage?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                PreparedStatement prepS = null;

                try (Connection conn = Sql.DbConnector();) {

                    String query = "delete from bagage where lost_id = ?";
                    prepS = conn.prepareStatement(query);
                    prepS.executeUpdate();
                    tableView4.getItems().remove(selectedIndex);
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

    public void editLuggageRecord(Stage primaryStage) {

        //niewe popup, de rest van de app bevriest.
        LuggageRecord2 recordToEdit = tableView4.getSelectionModel().getSelectedItem();

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
        Label status = new Label("status");
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

        //labelnrField.setText(recordToEdit.getLabelNr());
        //vluchtField.setText(recordToEdit.getFlightNr());
        //iataField.setText
//        resetEdit.setOnAction((ActionEvent e) -> {
//            deleteLuggage();
//        });
        form.add(confirmEdit, 1, 12);
        form.add(resetEdit, 2, 12);
        
        form.add(cancelEdit, 3, 12);

//        confirmEdit.setOnAction((ActionEvent e) -> {
//            try (Connection conn = Sql.DbConnector();) {
//                PreparedStatement pst;
//                String SQL = "UPDATE bagage set"
//                        + "(labelnr, vlucht, iata, lugType, merk, Prikleur, SecKleur, extra_info, status, datum_bevestiging) VALUES"
//                        + "(?,?,?,?,?,?,?,?,?,?)";
//
//                pst = conn.prepareStatement(SQL);
//                pst.setString(1, labelnrField.getText());
//                pst.setString(2, vluchtField.getText());
//                pst.setString(3, iataField.getText());
//                pst.setString(4, lugTypeField.getText());
//                pst.setString(5, merkField.getText());
//                pst.setString(6, prikleurField.getText());
//                pst.setString(7, seckleurField.getText());
//                pst.setString(8, extraInfoField.getText());
//                pst.setString(9, statusField.getText());
//                pst.setString(10, datumBevestigingField.getText());
//
//                System.out.println(SQL);
//                conn.createStatement().executeUpdate(SQL);
//            } catch (Exception e2) {
//                e2.printStackTrace();
//                System.out.println("Error on Building Data");
//            }
//            this.data.clear();
//            this.tableData.clear();
//            this.data = dbManager.getLuggageListFromDB();
//            for (int i = 0; i < data.size(); i++) {
//                tableData.add(data.get(i));
//            }
//            checkPopup.close();
//            
//            
//        });

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
        
        cancelEdit.setOnAction((ActionEvent e) -> {
            checkPopup.close();
            
        });

        form.setPadding(new Insets(0, 0, 0, 0));
        form.getChildren().addAll();

        Scene dialogScene = new Scene(form, 800, 1000);
        checkPopup.setScene(dialogScene);
        checkPopup.show();
    }

}
