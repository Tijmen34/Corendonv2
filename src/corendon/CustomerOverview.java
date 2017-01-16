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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ObservableList<CustomerRecord> searchResults
            = FXCollections.observableArrayList();

    private TableView<CustomerRecord> tableView4;

    private VBox controlBox = new VBox();
    private HBox topBar1 = new HBox();
    private HBox topBar2 = new HBox();
    private BorderPane border1 = new BorderPane();

    private Button refresh = new Button("Refresh table");
    private Button back = new Button("Back");
    private Button searchButton = new Button("Search");
    private TextField searchBar = new TextField();
    private Label tableStatus = new Label("Search customers:");
    private boolean isShowingSearch = false;


    public void initScreen() {
        dbManager = new DbManager();
        this.data = dbManager.getCustomerListFromDB();
        for (int i = 0; i < data.size(); i++) {
            tableData.add(data.get(i));
        }

        tableView4 = dbManager.createCustomerTable();
        
        this.setTop(topBar1);
        this.setRight(controlBox);
        this.setCenter(border1);
        border1.setCenter(tableView4);


        searchBar = new TextField();
        Image corLogo = new Image("Corendon.png");
        ImageView logo = new ImageView();
        logo.setImage(corLogo);
        logo.setFitWidth(300);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);
        tableView4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        topBar1.getChildren().addAll(topBar2, tableStatus, searchBar, searchButton, refresh);
        searchButton.setMinSize(20, 25);
        topBar1.setSpacing(30);
        topBar1.setMinHeight(50);
        topBar1.setAlignment(Pos.CENTER);
        topBar2.getChildren().addAll(logo);
        topBar2.setAlignment(Pos.CENTER_RIGHT);

        // ------------------------------------------

        tableView4.setMinSize(1115, (25 * 24) + 26);
        tableView4.setMaxSize(1115, (25 * 24) + 26);


        //tabellen vullen
        tableView4.setItems(this.tableData);
        //stickyData.clear();
        //--------------------------------------------

        refresh.setOnAction((ActionEvent e) -> {
            for (int i = 0; i < tableView4.getItems().size(); i++) {
                tableView4.getItems().clear();
            }
            updateData();

        });
        searchButton.setOnAction((ActionEvent e) -> {
            isShowingSearch = true;
            searchItems();
            tableStatus.setText("Search Results:");
            topBar1.getChildren().add(back);
        });

        back.setOnAction((ActionEvent e) -> {
            isShowingSearch = false;
            tableView4.setItems(tableData);
            tableStatus.setText("Search customers:");
            topBar1.getChildren().removeAll(back);
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
        data = dbManager.getCustomerListFromDB();
       for (int i = 0; i < this.data.size(); i++) {
           this.tableData.add(this.data.get(i));
       }
               tableView4.setItems(this.tableData);
    }
}
