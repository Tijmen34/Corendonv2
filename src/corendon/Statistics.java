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
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Burak
 */
public class Statistics extends BorderPane {

    ResultSet rs = null;
    PreparedStatement pst = null;
    Connection conn;
    Statement stmt;
    TextField wut = new TextField();
    Button get = new Button("get");
    Button chartB = new Button("chart");
    double value = 0.0;
    
    public double getValues() {
        
        
        get.setOnAction(e -> {
        try (Connection conn = Sql.DbConnector();) {
            
            String SQL = "SELECT COUNT(status) AS 'Total' FROM bagage WHERE status =?";
            pst = conn.prepareStatement(SQL);
            pst.setString(1, wut.getText());
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
            value = rs.getInt("Total");
            System.out.println(value);
            
            } else {
                System.out.println("error");
            }
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        } 
        
        });
        return value;
        
        
    }
                
    
    public void initScreen(Stage primaryStage) {
        
        wut.setPromptText("Wut");
        chartB.setOnAction(e -> {
        getValues();
        
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Missing", getValues()),
                        new PieChart.Data("Found", 25),
                        new PieChart.Data("Solved", 10));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);

        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 16px UniSansW01-LightItalic;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    caption.setTranslateX(e.getSceneX() - 100);
                    caption.setTranslateY(e.getSceneY());
                    caption.setText(String.valueOf(data.getPieValue()) + " cases");
                }
            });
        }
        
        
        this.setCenter(chart);
        this.setTop(caption);
        this.setMaxSize(500, 500);
        this.setMinSize(500, 500);

    });
        
        this.setBottom(wut);
        this.setRight(get);
        this.setLeft(chartB);
    }
               


}
