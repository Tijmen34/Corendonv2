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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.GroupLayout;

/**
 *
 * @author JerryJerr
 */
public class UsersOverview extends BorderPane {

    private DbManager dbManager;
    private Button addBut = new Button("Submit");
    private Button cancel = new Button("Cancel");
    private Button refresh = new Button("Refresh table");
    private Button delete = new Button("Delete from table");
    private Button b = new Button("Add user");
    private Stage primaryStage;
    private ObservableList<UserRecord> data
            = FXCollections.observableArrayList();
    private ObservableList<UserRecord> tableData
            = FXCollections.observableArrayList();
    private Connection conn;
    private PreparedStatement prepS = null;
    public  TableView<UserRecord> tableView4 = new TableView();

    public void initScreen(Stage primaryStage) {
        this.primaryStage = primaryStage;
        dbManager = new DbManager();




        data = dbManager.getUserListFromDB();
        for (int i = 0; i < this.data.size(); i++) {
            this.tableData.add(this.data.get(i));
        }

        Image corendonLogo = new Image("Corendon-Logo2.jpg");
        ImageView corendonLogoView = new ImageView();
        corendonLogoView.setImage(corendonLogo);
        corendonLogoView.setPreserveRatio(true);
        corendonLogoView.setFitHeight(800);
        corendonLogoView.setFitWidth(250);
        System.out.println(tableView4.getColumns().toString());


        HBox topBar = new HBox();
        BorderPane border1 = new BorderPane();
        ScrollPane scroll2 = new ScrollPane();
        VBox xbox = new VBox();
        TextField text1 = new TextField();
        GridPane table3 = new GridPane();
        tableView4 = dbManager.createUserTable();


        setTop(topBar);
        setCenter(border1);
        border1.setLeft(scroll2);
        border1.setRight(xbox);
        xbox.setPadding(new Insets(10,10,10,10));
        xbox.getChildren().addAll(text1, b, refresh, delete);
        text1.setAlignment(Pos.CENTER);
        scroll2.setContent(table3);
        scroll2.setMinSize(800,674);
        scroll2.setMaxSize(800,674);
        table3.add(tableView4, 2, 0, 10, (tableData.size() + 1));
        refresh.setOnAction((ActionEvent e) -> {
            updateData();
        });
        
        delete.setOnAction((ActionEvent e) -> {
            deletePerson();
        });




        // topbar voor corendon logo
        topBar.getChildren().addAll(corendonLogoView);
        topBar.setSpacing(30);
        topBar.setMinHeight(50);
        topBar.setAlignment(Pos.CENTER);

            b.setOnAction((ActionEvent e) -> {
            addUser(primaryStage);
        });

        //table niet resizable
        tableView4.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //table grootte veranderen en vullen
        tableView4.setMinSize(800,674);
        tableView4.setMaxSize(800,674);
        tableView4.setItems(this.tableData);

    }

    public void addUser(Stage primaryStage) {

        final GridPane grid1 = new GridPane();

        //spreekt voor zich
        Label usernameLB = new Label("Username:   ");
        Label passwordLB = new Label("Password    ");
        Label firstnameLB = new Label("First name:    ");
        Label tussenLB = new Label("Prefix:    ");
        Label surnameLB = new Label("Surname:   ");
        Label emailLB = new Label ("Email:    ");
        Label functionLB = new Label("Function:    ");



        //spreekt voor zich
        TextField usernameTX = new TextField();
                usernameTX.setPromptText("Username");
        TextField passwordTX = new TextField();
                passwordTX.setPromptText("Password");
        TextField firstnameTX = new TextField();
                firstnameTX.setPromptText("First name");
        TextField tussenTX = new TextField();
                tussenTX.setPromptText("Prefix");
        TextField surnameTX = new TextField();
                surnameTX.setPromptText("Surname");
        TextField emailTX = new TextField();
                emailTX.setPromptText("Email");
        TextField functionTX = new TextField();
                functionTX.setPromptText("Function");


        final Stage adduserStage = new Stage();
        adduserStage.initModality(Modality.APPLICATION_MODAL);
        adduserStage.initOwner(primaryStage);
        cancel.setMinSize(70, 20);
        addBut.setMinSize(70, 20);
        VBox useraddVragen = new VBox(10);
        VBox useraddAntwoorden = new VBox(10);
        HBox buttonBox = new HBox(10);
        useraddVragen.setPadding(new Insets(10, 10, 10, 10));

        // buttons toevoegen aan een hbox, toevoegen aan grid en de padding aanpassen
        buttonBox.getChildren().addAll(addBut, cancel);
        grid1.getChildren().addAll(useraddVragen, useraddAntwoorden, buttonBox);
        addBut.setPadding(new Insets(1, 1, 1, 1));
        cancel.setPadding(new Insets(1, 1 ,1, 1));
        cancel.setTextAlignment(TextAlignment.LEFT);
        cancel.setAlignment(Pos.CENTER_LEFT);

        // plaatsen van alle labels, buttons en textfields
        grid1.setVgap(15);
        grid1.setHgap(15);
        grid1.add(usernameLB, 1, 1);
        grid1.add(usernameTX, 2, 1);
        grid1.add(passwordLB, 1, 2);
        grid1.add(passwordTX, 2, 2);
        grid1.add(firstnameLB, 1, 3);
        grid1.add(firstnameTX, 2, 3);
        grid1.add(tussenLB, 1, 4);
        grid1.add(tussenTX, 2, 4);
        grid1.add(surnameLB, 1, 5);
        grid1.add(surnameTX, 2, 5);
        grid1.add(functionLB, 1, 6);
        grid1.add(functionTX, 2, 6);
        grid1.add(addBut, 2, 7);
        grid1.add(cancel, 3, 7);
        
//addBut leest de gegevens in en zet ze in database
        addBut.setOnAction((ActionEvent e) -> {
            PreparedStatement prepS = null;
            try (Connection conn = Sql.DbConnector();) {
                primaryStage.setTitle("Adding user");

                String query = "insert into users"
                        + "(username, password, firstname, tussenvoegsel, surname, email,  function) VALUES"
                        + "(?, ?, ?, ?, ?, ?)";
                prepS = conn.prepareStatement(query);
                prepS.setString(1, usernameTX.getText());
                prepS.setString(2, passwordTX.getText());
                prepS.setString(3, firstnameTX.getText());
                prepS.setString(4, tussenTX.getText());
                prepS.setString(5, surnameTX.getText());
                prepS.setString(6, functionTX.getText());

                if (usernameTX.getText().isEmpty() || passwordTX.getText().isEmpty() || firstnameTX.getText().isEmpty()
                        || surnameTX.getText().isEmpty() || functionTX.getText().isEmpty()) {

                    Alert alert1 = new Alert(Alert.AlertType.WARNING);
                    alert1.setTitle("Adding user");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Some information is not filled in, please try again.");
                    alert1.showAndWait();

                } else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Adding user");
                    alert.setHeaderText(null);
                    alert.setContentText("User added succesfully");
                    alert.showAndWait();
                    adduserStage.close();
                    prepS.executeUpdate();

                }
            } catch (Exception e1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Corendon - Luggage");
                alert.setHeaderText(null);
                alert.setContentText("There is an error in the database, please try again later.");
                alert.showAndWait();
                System.out.println("SQL ERROR");
                System.err.println(e1);

            }

        });
        cancel.setOnAction((ActionEvent e) -> {
            adduserStage.close();
        });

        //scene bouwen enzo
        Scene dialogScene = new Scene(grid1, 450, 400);
        adduserStage.setScene(dialogScene);
        adduserStage.show();

    }

    //onuitgewerkte refreshknop
    public void updateData() {
        for ( int i = 0; i<tableView4.getItems().size(); i++) {
    tableView4.getItems().clear();
    
}
tableData.clear();
       for (int i = 0; i < this.data.size(); i++) {
           this.tableData.add(this.data.get(i));
        }

    }

    public void deletePerson() {

        int selectedIndex = tableView4.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Delete person?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
            alert.setHeaderText(null);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                tableView4.getItems().remove(selectedIndex);
                PreparedStatement prepS = null;

                try (Connection conn = Sql.DbConnector();) {

                    String query = "delete from users where user_id = ? and username = ?";
                    prepS = conn.prepareStatement(query);
                    prepS.setString(1, tableView4.getSelectionModel().getSelectedItem().getUser_id());
                    prepS.setString(1, tableView4.getSelectionModel().getSelectedItem().getUsername());
                    prepS.executeUpdate();

                } catch (Exception e1) {
                    System.out.println("SQL ERROR");
                    System.err.println(e1);

                }

            }

        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

}
