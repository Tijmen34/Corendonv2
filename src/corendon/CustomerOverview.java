/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Tijmen
 */
public class CustomerOverview extends BorderPane {
private DbManager dbManager;

    private ObservableList<CustomerRecord> data
            = FXCollections.observableArrayList();
    private ObservableList<CustomerRecord> tableData
            = FXCollections.observableArrayList();
    private ObservableList<CustomerRecord> stickyData
            = FXCollections.observableArrayList();
    private ObservableList<CustomerRecord> searchResults
            = FXCollections.observableArrayList();

    private TableView<CustomerRecord> tableViewSticky3;
    private TableView<CustomerRecord> tableView4;

    private VBox controlBox = new VBox();
    private ScrollPane tableScroll = new ScrollPane();
    private StackPane stickyBox = new StackPane();
    private HBox topBar = new HBox();
    private BorderPane border1 = new BorderPane();

    private Button selToStickyBtn = new Button("^^");
    private Button selUnStickyBtn = new Button("vv");

    private Button back = new Button("back");
    private Button searchButton = new Button("search");
    private TextField searchBar = new TextField();
    private Label tableStatus = new Label("Overview:");

    private boolean isShowingSearch;

    public void initScreen() {
        dbManager = new DbManager();
        this.data = dbManager.getCustomerListFromDB();
        for (int i = 0; i < data.size(); i++) {
            tableData.add(data.get(i));
        }

        tableView4 = dbManager.createCustomerTable();
        tableViewSticky3 = dbManager.createCustomerTable();
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
        controlBox.getChildren().addAll(selUnStickyBtn, selToStickyBtn);
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
        for (CustomerRecord record : tableData) {
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

    public void updateData() {
        stickyData.clear();
        tableData.clear();
        for (CustomerRecord record : data) {
            tableData.add(record);
        }
    }
}
