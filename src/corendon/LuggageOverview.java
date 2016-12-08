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
import javafx.event.EventType;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jeroen de Jong
 */
public class LuggageOverview extends BorderPane {

    private ObservableList<LuggageRecord2> data
            = FXCollections.observableArrayList(
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true),
//                    new LuggageRecord2("0001", "3R5F2", "MH370",
//                            "Suitcase", "jemoeder", "Red", "Black",
//                            "NULL", "12324", "Missing", false),
//                    new LuggageRecord2("0002", "T43RS", "MH370",
//                            "Suitcase", "jeopa", "Red", "Pink",
//                            "lelijk", "12743", "Missing", false),
//                    new LuggageRecord2("0003", "TXZ35", "MH18",
//                            "sportsbag", "nike", "Yellow", "Null",
//                            "Null", "85394", "Missing", false),
//                    new LuggageRecord2("0004", "P05YR", "MH18",
//                            "sportsbag", "jema", "Blue", "Null",
//                            "Null", "81254", "Missing", true)
            );
    private ObservableList<LuggageRecord2> tableData
            = FXCollections.observableArrayList();
    private ObservableList<LuggageRecord2> stickyData
            = FXCollections.observableArrayList();

    //private GridPane table3 = new GridPane();
    //private GridPane tableSticky2 = new GridPane();
    private VBox controlBox = new VBox();
    private Button selToStickyBtn = new Button("^^");
    private Button selUnStickyBtn = new Button("vv");
    private ScrollPane tableScroll = new ScrollPane();
    private StackPane stickyBox = new StackPane();

    public void initScreen() {

        int recordIterator = 0;
        
        getRecordsFromDB();
        for(int i = 0; i < data.size(); i++) {
            tableData.add(data.get(i));
        }
        

        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();

        //ScrollPane scroll2 = new ScrollPane();
        final TableView<LuggageRecord2> tableViewSticky3 = new TableView();
        final TableView<LuggageRecord2> tableView4 = new TableView();

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
        //tableScroll.setContent(tableView4);
        //tableScroll.setPrefSize(1000, 600);
        //tableSticky2.add(tableViewSticky3, 2, 0, 10, stickyData.size() + 1);
        //scroll2.setContent(table3);
        //table3.add(tableView4, 2, 0, 10, (tableData.size() + 1));

        //-------------------------------------------
        //balk met controls voor tabel rechts
        controlBox.getChildren().addAll(selUnStickyBtn, selToStickyBtn);

        //-------------------------------------------
        //Rode balk bovenin het scherm
        TextField searchBar = new TextField();
        Button searchButton = new Button();

        topBar.getChildren().addAll(searchBar, searchButton);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color:#D81E05");
        // ------------------------------------------

        //Tabel
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

        tableView4.getColumns().addAll(lostIdCol, labelNrCol, flightNrCol,
                typeCol, brandCol, primaryColorCol, secondaryColorCol, infoCol,
                customerIdCol, statusCol, dateCol, timeCol);
        tableView4.setMinSize(1000, (30 * 24) + 26);
        tableView4.setMaxSize(1000, (30 * 24) + 26);

        //-------------------------------------------
        //Sticky Tabel
        TableColumn lostIdColSt = new TableColumn("Lost ID");
        TableColumn labelNrColSt = new TableColumn("Label nr");
        TableColumn flightNrColSt = new TableColumn("Flight nr");
        TableColumn typeColSt = new TableColumn("Type");
        TableColumn brandColSt = new TableColumn("Brand Name");
        TableColumn primaryColorColSt = new TableColumn("Color 1");
        TableColumn secondaryColorColSt = new TableColumn("Color 2");
        TableColumn infoColSt = new TableColumn("Add. info");
        TableColumn customerIdColSt = new TableColumn("Customer ID");
        TableColumn statusColSt = new TableColumn("status");
        TableColumn dateColSt = new TableColumn("date");
        TableColumn timeColSt = new TableColumn("time");

        lostIdColSt.setCellValueFactory(
                new PropertyValueFactory<>("lostId"));
        labelNrColSt.setCellValueFactory(
                new PropertyValueFactory<>("labelNr"));
        flightNrColSt.setCellValueFactory(
                new PropertyValueFactory<>("flightNr"));
        typeColSt.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        brandColSt.setCellValueFactory(
                new PropertyValueFactory<>("brandName"));
        primaryColorColSt.setCellValueFactory(
                new PropertyValueFactory<>("primaryColor"));
        secondaryColorColSt.setCellValueFactory(
                new PropertyValueFactory<>("secondaryColor"));
        infoColSt.setCellValueFactory(
                new PropertyValueFactory<>("info"));
        customerIdColSt.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));
        statusColSt.setCellValueFactory(
                new PropertyValueFactory<>("status"));
        dateColSt.setCellValueFactory(
                new PropertyValueFactory<>("date"));
        timeColSt.setCellValueFactory(
                new PropertyValueFactory<>("time"));

        tableViewSticky3.getColumns().addAll(lostIdColSt, labelNrColSt, flightNrColSt,
                typeColSt, brandColSt, primaryColorColSt, secondaryColorColSt, infoColSt,
                customerIdColSt, statusColSt,dateColSt, timeColSt);
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
        //--------------------------------------------

        //Buttons functioneel
        selToStickyBtn.setOnAction((ActionEvent e) -> {

            stickyData.add(tableData.get(tableView4.getSelectionModel().getSelectedIndex()));
            tableData.remove(tableData.get(tableView4.getSelectionModel().getSelectedIndex()));
            stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
            tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);

        });

        selUnStickyBtn.setOnAction((ActionEvent e) -> {

            tableData.add(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
            stickyData.remove(stickyData.get(tableViewSticky3.getSelectionModel().getSelectedIndex()));
            stickyBox.setPrefSize(1000, (stickyData.size() * 24) + 26);
            tableViewSticky3.setPrefSize(1000, (stickyData.size() * 24) + 26);

        });
    }

    public void moveToSticky(int buttonIterator, LuggageRecord2 record, TableView table) {
        this.stickyData.add(record);
        this.tableData.remove(buttonIterator);
        table.setPrefSize(900, 30 + 30);
        //this.unSticky.add(this.sticky)
        //this.sticky.remove(this.sticky.get(buttonIterator));    
    }

    public void getRecordsFromDB() {
        try (Connection conn = Sql.DbConnector();) {
            String SQL = "SELECT * FROM bagage";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                this.data.add(new LuggageRecord2(rs.getString("lost_id"), 
                        rs.getString("labelnr"), rs.getString("vlucht"), 
                        rs.getString("lugType"), rs.getString("merk"), 
                        rs.getString("PriKleur"), rs.getString("SecKleur"),
                        "vermist", "yolo", rs.getString("status"), 
                        rs.getString("datum_bevestiging").substring(0, Math.min(rs.getString("datum_bevestiging").length(), 9)), 
                        rs.getString("datum_bevestiging").substring(11, Math.min(rs.getString("datum_bevestiging").length(), 18))));
            }  
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}
