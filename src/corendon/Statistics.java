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
import java.time.LocalDate;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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

    double valueTotal = 0.0;
    double valueFound = 0.0;
    double valueMissing = 0.0;
    double valueSolved = 0.0;

    Button submitDate = new Button("Retrieve");
    Button chooseinfo = new Button("Submit");
    Button back = new Button("Go back");
    Label dateChoose = new Label("Select two dates to view statistics of all cases");
    Label label1 = new Label("From: ");
    Label label2 = new Label("To: ");
    Label empty = new Label("");
    DatePicker date1 = new DatePicker();
    DatePicker date2 = new DatePicker();
    VBox vbox = new VBox();
    HBox hbox = new HBox();
    HBox hbox1 = new HBox();
    String pattern = "yyyy-MM-dd";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    String date1Formatted;
    String date2Formatted;
    ComboBox statinfo = new ComboBox(FXCollections.observableArrayList("Pie chart", "Graph", "Both"));
    Stage primaryStage;
    
    public void getValues() {

        try (Connection conn = Sql.DbConnector();) {

            String SQL1 = "select count(*) total, sum(case when status = 'found' and datum_bevestiging between ? AND ? then 1 else 0 end) found, sum(case when status = 'lost' and datum_bevestiging between ? AND ? then 1 else 0 end) lost, sum(case when status = 'solved' and datum_bevestiging between ? AND ? then 1 else 0 end) solved from bagage";

            pst = conn.prepareStatement(SQL1);
            pst.setString(1, date1Formatted);
            pst.setString(3, date1Formatted);
            pst.setString(5, date1Formatted);
            pst.setString(2, date2Formatted);
            pst.setString(4, date2Formatted);
            pst.setString(6, date2Formatted);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                valueTotal = rs.getInt("total");
                valueFound = rs.getInt("found");
                valueMissing = rs.getInt("lost");
                valueSolved = rs.getInt("solved");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public LineChart createGraph() {
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //creating the chart
        final LineChart<Number, Number> lineChart
                = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Total cases");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("Test");
        //populating the series with data
        series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));

        lineChart.getData().add(series);
        
        return lineChart;

    }


    public PieChart createChart() {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Total", valueTotal),
                        new PieChart.Data("Missing", valueMissing),
                        new PieChart.Data("Found", valueFound),
                        new PieChart.Data("Solved", valueSolved));

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Lost & Found luggage statistics\nClick on a 'slice' to see amount");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.BOTTOM);

        final Label caption = new Label("");
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font-weight: bold;");

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
        chart.setMaxSize(500, 500);
        chart.setMinSize(500, 500);
        this.setTop(caption);
        return chart;
    }

    public void initScreen(Stage primaryStage) {
        
        submitDate.setOnAction(e -> {
            addStats();
            LocalDate date1Unformatted = date1.getValue();
            LocalDate date2Unformatted = date2.getValue();
            date1Formatted = formatter.format(date1Unformatted);
            date2Formatted = formatter.format(date2Unformatted);

            statinfo.getSelectionModel().select(0);
            getValues();
            this.setCenter(createChart());
            this.setBottom(hbox1);
        });

        hbox.setStyle("-fx-background-color:#D81E05");

        this.setCenter(vbox);
        this.setTop(hbox);

        hbox.getChildren().addAll(new Label(""));
        vbox.getChildren().addAll(dateChoose, label1, date1, label2, date2, submitDate);

        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 50, 50, 50));

        dateChoose.setAlignment(Pos.CENTER);

        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

    }

    public void addStats() {

        hbox1.getChildren().addAll(statinfo, chooseinfo, back);
                
        chooseinfo.setOnAction(e -> {
            
                this.getChildren().removeAll(getCenter(), getRight(), getLeft());
                
            if (statinfo.getSelectionModel().getSelectedItem() == "Pie chart") {
                this.setCenter(createChart());
            } else if (statinfo.getSelectionModel().getSelectedItem() == "Graph") {
                this.setCenter(createGraph());
            } else if (statinfo.getSelectionModel().getSelectedItem() == "Both") {
                this.setLeft(createChart());
                this.setRight(createGraph());
            }
        });
        
        back.setOnAction(e -> {
            this.getChildren().clear();
            hbox.getChildren().clear();
            vbox.getChildren().clear();
            hbox1.getChildren().clear();
            this.initScreen(primaryStage);
        });
        
    }
}
