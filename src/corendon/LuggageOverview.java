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
import javafx.scene.layout.VBox;

/**
 *
 * @author iS109-3
 */
public class LuggageOverview extends BorderPane {

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
    private BorderPane border1 = new BorderPane();

    private Button selToStickyBtn = new Button("^^");
    private Button selUnStickyBtn = new Button("vv");
    private Button stickyMatchBtn = new Button("Match!");

    private Button back = new Button("back");
    private Button searchButton = new Button("search");
    private TextField searchBar = new TextField();
    private Label tableStatus = new Label("Overview:");

    private boolean isShowingSearch;

    public void initScreen() {
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
        controlBox.getChildren().addAll(selUnStickyBtn, stickyMatchBtn, selToStickyBtn);
        controlBox.setSpacing(50);

        //-------------------------------------------
        //Rode balk bovenin het scherm
        searchBar = new TextField();

        topBar.getChildren().addAll(tableStatus, searchBar, searchButton);
        searchButton.setMinSize(20, 100);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color:#D81E05");
        // ------------------------------------------

        tableView4.setMinSize(1000, (22 * 24) + 26);
        tableView4.setMaxSize(1000, (22 * 24) + 26);

        //-------------------------------------------
        //Sticky Tabel
        tableViewSticky3.setMinSize(1000, 24 + 26);
        tableViewSticky3.setPrefSize(1000, 24 + 26);
        tableViewSticky3.setMaxWidth(1000);
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
        });

        back.setOnAction((ActionEvent e) -> {
            isShowingSearch = false;
            tableView4.setItems(tableData);
            tableData.removeAll(stickyData);
            tableStatus.setText("Overview:");
            topBar.getChildren().setAll(tableStatus, searchBar, searchButton);
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
                    lost =  stickyData.get(1);
                } else {
                    found = stickyData.get(1);
                    lost =  stickyData.get(0);
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
                pst.setString(10,lost.getCustomerId());
                        //                LuggageRecord2 solvedCase = new LuggageRecord(found.getLostId(),
                        //                        found.getLabelNr(), lost.getFlightNr(),
                        //                        found.getType(), found.getBrandName(),
                        //                        found.getPrimaryColor(), found.getSecondaryColor(),
                        //                        found.getInfo(), lost.getCustomerId(), "solved", )
                
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

}
