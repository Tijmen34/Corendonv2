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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author iS109-3
 */
public class DbManager {

    private Stage primaryStage;
    private ResultSet rs = null;
    private PreparedStatement pst = null;
    private Connection conn;
    private Statement stmt;

    public DbManager() {
    }

    public ObservableList<LuggageRecord2> getLuggageListFromDB() {

        ObservableList<LuggageRecord2> data = FXCollections.observableArrayList();

        try (Connection conn = Sql.DbConnector();) {
            String SQL = "SELECT * FROM bagage";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            data.clear();
            while (rs.next()) {
                data.add(new LuggageRecord2(rs.getString("lost_id"),
                        rs.getString("labelnr"), rs.getString("vlucht"), rs.getString("iata"),
                        rs.getString("lugType"), rs.getString("merk"),
                        rs.getString("PriKleur"), rs.getString("SecKleur"),
                        "", "", rs.getString("status"),
                        rs.getString("datum_bevestiging").substring(0, Math.min(rs.getString("datum_bevestiging").length(), 9)),
                        rs.getString("datum_bevestiging").substring(11, Math.min(rs.getString("datum_bevestiging").length(), 18))));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<UserRecord> getUserListFromDB() {

        ObservableList<UserRecord> data = FXCollections.observableArrayList();

        try (Connection conn = Sql.DbConnector();) {
            String SQL = "SELECT * FROM users";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new UserRecord(rs.getString("user_id"), rs.getString("username"),
                        rs.getString("password"), rs.getString("firstname"),
                        rs.getString("tussenvoegsel"), rs.getString("surname"),
                        rs.getString("email"), rs.getString("function"), rs.getString("lost_password")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<CustomerRecord> getCustomerListFromDB() {

        ObservableList<CustomerRecord> data = FXCollections.observableArrayList();

        try (Connection conn = Sql.DbConnector();) {
            String SQL = "SELECT * FROM klant";
            ResultSet rs = conn.createStatement().executeQuery(SQL);
            while (rs.next()) {
                data.add(new CustomerRecord(rs.getString("klant_id"), rs.getString("geslacht"),
                        rs.getString("naam"), rs.getString("tussenvoegsel"),
                        rs.getString("achternaam"), rs.getString("gebdatum"),
                        rs.getString("straat"), rs.getString("huisnummer"), rs.getString("plaats"), rs.getString("postcode"),
                        rs.getString("land"), rs.getString("telnr"), rs.getString("mail")));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public ObservableList<LuggageRecord2> checkLabel(String labelnr) {

        ObservableList<LuggageRecord2> data = FXCollections.observableArrayList();

        //records met zelfde labelnr ophalen
        try (Connection conn = Sql.DbConnector();) {

            String query = "SELECT * FROM bagage WHERE labelnr=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, labelnr);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                data.add(new LuggageRecord2(rs.getString("lost_id"),
                        rs.getString("labelnr"), rs.getString("vlucht"), rs.getString("iata"),
                        rs.getString("lugType"), rs.getString("merk"),
                        rs.getString("PriKleur"), rs.getString("SecKleur"),
                        "", "", rs.getString("status"),
                        rs.getString("datum_bevestiging").substring(0, Math.min(rs.getString("datum_bevestiging").length(), 9)),
                        rs.getString("datum_bevestiging").substring(11, Math.min(rs.getString("datum_bevestiging").length(), 18))));
            }
        } catch (Exception e) {
            System.out.println("Error on Building Data");
        }
        return data;
    }

    public TableView createLuggageTable() {
        final TableView<LuggageRecord2> tableView = new TableView();

        TableColumn lostIdCol = new TableColumn("Lost ID");
        TableColumn labelNrCol = new TableColumn("Label nr");
        TableColumn flightNrCol = new TableColumn("Flight nr");
        TableColumn iataCol = new TableColumn("iata");
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
        iataCol.setCellValueFactory(
                new PropertyValueFactory<>("iata"));
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

        tableView.getColumns().addAll(lostIdCol, labelNrCol, flightNrCol, iataCol,
                typeCol, brandCol, primaryColorCol, secondaryColorCol, infoCol,
                customerIdCol, statusCol, dateCol, timeCol);

        return tableView;
    }

    public TableView createUserTable() {
        final TableView<UserRecord> tableView = new TableView();

        //tabel colummen declareren
        TableColumn userIdCol = new TableColumn("User ID");
        TableColumn usernameCol = new TableColumn("Username");
        TableColumn passwordCol = new TableColumn("Password");
        TableColumn firstnameCol = new TableColumn("First name");
        TableColumn tussenvoegselCol = new TableColumn("Tussenvoegsel");
        TableColumn surnameCol = new TableColumn("Surname");
        TableColumn emailCol = new TableColumn("Email");
        TableColumn functionCol = new TableColumn("Function");
        TableColumn lostpwCol = new TableColumn("Lost password");

        //table colommen klaarmaken voor gebruik
        userIdCol.setCellValueFactory(
                new PropertyValueFactory<>("user_id"));
        usernameCol.setCellValueFactory(
                new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(
                new PropertyValueFactory<>("password"));
        firstnameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstname"));
        tussenvoegselCol.setCellValueFactory(
                new PropertyValueFactory<>("tussenvoegsel"));
        surnameCol.setCellValueFactory(
                new PropertyValueFactory<>("surname"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("email"));
        functionCol.setCellValueFactory(
                new PropertyValueFactory<>("function"));
        lostpwCol.setCellValueFactory(
                new PropertyValueFactory<>("lost_password"));
        //colommen toevoegen aan tabel
        tableView.getColumns().addAll(userIdCol, usernameCol, passwordCol, firstnameCol, tussenvoegselCol,
                surnameCol, emailCol, functionCol, lostpwCol);

        return tableView;
    }

    public TableView createCustomerTable() {
        final TableView<CustomerRecord> tableView = new TableView();

        TableColumn clientidCol = new TableColumn("Client ID");
        TableColumn genderCol = new TableColumn("Gender");
        TableColumn firstnameCol = new TableColumn("First name");
        TableColumn tussenCol = new TableColumn("Prefix");
        TableColumn surnameCol = new TableColumn("Surname");
        TableColumn datebirthCol = new TableColumn("Date of Birth");
        TableColumn streetCol = new TableColumn("Street");
        TableColumn housenrCol = new TableColumn("House number");
        TableColumn townCol = new TableColumn("Town");
        TableColumn zipCol = new TableColumn("Zip code");
        TableColumn countryCol = new TableColumn("Country");
        TableColumn phonenrCol = new TableColumn("Phone number");
        TableColumn emailCol = new TableColumn("Email");

        clientidCol.setCellValueFactory(
                new PropertyValueFactory<>("customerId"));
        genderCol.setCellValueFactory(
                new PropertyValueFactory<>("gender"));
        firstnameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstname"));
        tussenCol.setCellValueFactory(
                new PropertyValueFactory<>("tussenVoegsel"));
        surnameCol.setCellValueFactory(
                new PropertyValueFactory<>("surname"));
        datebirthCol.setCellValueFactory(
                new PropertyValueFactory<>("dateOfBirth"));
        streetCol.setCellValueFactory(
                new PropertyValueFactory<>("street"));
        housenrCol.setCellValueFactory(
                new PropertyValueFactory<>("houseNr"));
        townCol.setCellValueFactory(
                new PropertyValueFactory<>("town"));
        zipCol.setCellValueFactory(
                new PropertyValueFactory<>("zipCode"));
        countryCol.setCellValueFactory(
                new PropertyValueFactory<>("country"));
        phonenrCol.setCellValueFactory(
                new PropertyValueFactory<>("phoneNr"));
        emailCol.setCellValueFactory(
                new PropertyValueFactory<>("eMail"));

        tableView.getColumns().addAll(clientidCol, genderCol, firstnameCol, tussenCol,
                surnameCol, datebirthCol, streetCol, housenrCol, townCol,
                zipCol, countryCol, phonenrCol, emailCol);

        return tableView;
    }
}
