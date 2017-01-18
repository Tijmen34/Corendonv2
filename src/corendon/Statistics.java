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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private ResultSet rs = null;
    private PreparedStatement pst = null;

    //de waardes voor de piechart
    private double valueTotal = 0.0;
    private double valueFound = 0.0;
    private double valueMissing = 0.0;
    private double valueSolved = 0.0;
    private double valueDelivered = 0.0;
    
    //de waardes voor de graph
    private int valueJan = 0;
    private int valueFeb = 0;
    private int valueMar = 0;
    private int valueApr = 0;
    private int valueMay = 0;
    private int valueJun = 0;
    private int valueJul = 0;
    private int valueAug = 0;
    private int valueSep = 0;
    private int valueOct = 0;
    private int valueNov = 0;
    private int valueDec = 0;
    
    //alle buttons in dit scherm
    private Button submitDate = new Button("Retrieve");
    private Button chooseinfo = new Button("Submit");
    private Button back = new Button("Go back");
    
    //alle labels
    private Label dateChoose = new Label("Enter two years to retrieve information about cases between those years");
    private Label label1 = new Label("From: ");
    private Label label2 = new Label("To: ");
    private Label empty = new Label("");
    private Label caption = new Label("");
    
    //textfields
    private TextField date1 = new TextField();
    private TextField date2 = new TextField();
    
    //vbox is voor date submit
    //hboxen zijn voor de bovenkant van het scherm
    private VBox vbox = new VBox();
    private HBox hbox = new HBox();
    private HBox hbox1 = new HBox();
    
    //format de ingevoerde date van jaar naar yyyy-mm-dd
    private String date1Formatted;
    private String date2Formatted;
    
    private ComboBox statinfo = new ComboBox(FXCollections.observableArrayList("Pie chart", "Graph"));
    private Stage primaryStage;
    
    /*
    * de omgezette data die worden geinsert in de query voor de graph 
    * date1 = van date2 = tot
    */
    private String date1jan;
    private String date1feb;
    private String date1mar;
    private String date1apr;
    private String date1may;
    private String date1jun;
    private String date1jul;
    private String date1aug;
    private String date1sep;
    private String date1oct;
    private String date1nov;
    private String date1dec;
    
    private String date2jan;
    private String date2feb;
    private String date2mar;
    private String date2apr;
    private String date2may;
    private String date2jun;
    private String date2jul;
    private String date2aug;
    private String date2sep;
    private String date2oct;
    private String date2nov;
    private String date2dec;
    
    //haalt de waardes uit de DB voor piechart; zie query
    public void getValues() {
        
        try (Connection conn = Sql.DbConnector();) {
                            
            String SQL1 = "select count(*) total, "
                    + "sum(case when status = 'found' and datum_bevestiging between ? AND ? then 1 else 0 end) found, "
                    + "sum(case when status = 'lost' and datum_bevestiging between ? AND ? then 1 else 0 end) lost, "
                    + "sum(case when status = 'solved' and datum_bevestiging between ? AND ? then 1 else 0 end) solved, "
                    + "sum(case when status = 'delivered' and datum_bevestiging between ? AND ? then 1 else 0 end) delivered "
                    + "from bagage";

            pst = conn.prepareStatement(SQL1);
            pst.setString(1, date1Formatted);
            pst.setString(3, date1Formatted);
            pst.setString(5, date1Formatted);
            pst.setString(7, date1Formatted);
            pst.setString(2, date2Formatted);
            pst.setString(4, date2Formatted);
            pst.setString(6, date2Formatted);
            pst.setString(8, date2Formatted);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                valueTotal = rs.getInt("total");
                valueFound = rs.getInt("found");
                valueMissing = rs.getInt("lost");
                valueSolved = rs.getInt("solved");
                valueDelivered = rs.getInt("delivered");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
    
    //deze keer voor grafiek
    public void getValuesGraph() {
        
        date1jan = date1.getText() + "-01-01";
        date1feb = date1.getText() + "-02-01";
        date1mar = date1.getText() + "-03-01";
        date1apr = date1.getText() + "-04-01";
        date1may = date1.getText() + "-05-01";
        date1jun = date1.getText() + "-06-01";
        date1jul = date1.getText() + "-07-01";
        date1aug = date1.getText() + "-08-01";
        date1sep = date1.getText() + "-09-01";
        date1oct = date1.getText() + "-10-01";
        date1nov = date1.getText() + "-11-01";
        date1dec = date1.getText() + "-12-01";

        date2jan = date1.getText() + "-01-31";
        date2feb = date1.getText() + "-02-31";
        date2mar = date1.getText() + "-03-31";
        date2apr = date1.getText() + "-04-31";
        date2may = date1.getText() + "-05-31";
        date2jun = date1.getText() + "-06-31";
        date2jul = date1.getText() + "-07-31";
        date2aug = date1.getText() + "-08-31";
        date2sep = date1.getText() + "-09-31";
        date2oct = date1.getText() + "-10-31";
        date2nov = date1.getText() + "-11-31";
        date2dec = date1.getText() + "-12-31";
        
        try (Connection conn = Sql.DbConnector();) {

            String SQL1 = "select count(*) total," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) jan," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) feb," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) mar," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) apr," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) may," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) jun," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) jul," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) aug," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) sep," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) oct," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) nov," +
                "sum(case when datum_bevestiging between ? and ? then 1 else 0 end) 'dec'" +

                "from bagage";

            pst = conn.prepareStatement(SQL1);
            pst.setString(1, date1jan);
            pst.setString(2, date2jan);
            pst.setString(3, date1feb);
            pst.setString(4, date2feb);
            pst.setString(5, date1mar);
            pst.setString(6, date2mar);
            pst.setString(7, date1apr);
            pst.setString(8, date2apr);
            pst.setString(9, date1may);
            pst.setString(10, date2may);
            pst.setString(11, date1jun);
            pst.setString(12, date2jun);
            pst.setString(13, date1jul);
            pst.setString(14, date2jul);
            pst.setString(15, date1aug);
            pst.setString(16, date2aug);
            pst.setString(17, date1sep);
            pst.setString(18, date2sep);
            pst.setString(19, date1oct);
            pst.setString(20, date2oct);
            pst.setString(21, date1nov);
            pst.setString(22, date2nov);
            pst.setString(23, date1dec);
            pst.setString(24, date2dec);

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                valueJan = rs.getInt("jan");
                valueFeb = rs.getInt("feb");
                valueMar = rs.getInt("mar");
                valueApr = rs.getInt("apr");
                valueMay = rs.getInt("may");
                valueJun = rs.getInt("jun");
                valueJul = rs.getInt("jul");
                valueAug = rs.getInt("aug");
                valueSep = rs.getInt("sep");
                valueOct = rs.getInt("oct");
                valueNov = rs.getInt("nov");
                valueDec = rs.getInt("dec");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    //maakt grafiek
    public LineChart createGraph() {
        //definieert de assen
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Number of Month");
        //create de grafiek
        final LineChart<Number, Number> lineChart
                = new LineChart<Number, Number>(xAxis, yAxis);

        lineChart.setTitle("Total cases of year: " + date1.getText());
        //definieer de naam van een as
        XYChart.Series series = new XYChart.Series();
        series.setName("Total cases");
        //voeg data toe an assen
        series.getData().add(new XYChart.Data(1, valueJan));
        series.getData().add(new XYChart.Data(2, valueFeb));
        series.getData().add(new XYChart.Data(3, valueMar));
        series.getData().add(new XYChart.Data(4, valueApr));
        series.getData().add(new XYChart.Data(5, valueMay));
        series.getData().add(new XYChart.Data(6, valueJun));
        series.getData().add(new XYChart.Data(7, valueJul));
        series.getData().add(new XYChart.Data(8, valueAug));
        series.getData().add(new XYChart.Data(9, valueSep));
        series.getData().add(new XYChart.Data(10, valueOct));
        series.getData().add(new XYChart.Data(11, valueNov));
        series.getData().add(new XYChart.Data(12, valueDec));

        lineChart.getData().add(series);

        return lineChart;

    }

    //methode voor de piechart
    public PieChart createChart() {
        //voeg data toe aan de piechart
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Total", valueTotal),
                        new PieChart.Data("Missing", valueMissing),
                        new PieChart.Data("Found", valueFound),
                        new PieChart.Data("Delivered", valueDelivered),
                        new PieChart.Data("Solved", valueSolved));

        //deze vars worden gebruikt om de waardes van een 'slice' te returnen
        int IntPie1 = (int) pieChartData.get(0).getPieValue();
        int IntPie2 = (int) pieChartData.get(1).getPieValue();
        int IntPie3 = (int) pieChartData.get(2).getPieValue();
        int IntPie4 = (int) pieChartData.get(3).getPieValue();
        int IntPie5 = (int) pieChartData.get(4).getPieValue();

        //maakt een object van type piechart
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Lost & Found luggage statistics");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.BOTTOM);
        chart.setStartAngle(90);
        chart.setClockwise(true);
        caption.setTextFill(Color.BLACK);
        caption.setStyle("-fx-font-weight: bold; -fx-font-size: 18;");
        caption.setText("Total Cases: " + IntPie1
                + " | Missing Cases: " + IntPie2
                + " | Found Cases: " + IntPie3
                + " | Delivered: " + IntPie4
                + " | Solved: " + IntPie5);

        return chart;
    }

    //het hele scherm
    public void initScreen(Stage primaryStage) {

        submitDate.setOnAction(e -> {
            //checkt eerst of schermen zijn ingevoerd
            if (date1.getText().isEmpty() == true
                    || date2.getText().isEmpty() == true
                    || Integer.valueOf(date1.getText()) >= Integer.valueOf(date2.getText())) {
                
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Corendon - Luggage");
                alert.setHeaderText(null);
                alert.setContentText("Please make sure your input is correct.");
                alert.showAndWait();
            } else {
                date1Formatted = date1.getText() + "-01-01";
                date2Formatted = date2.getText() + "-01-01";
                
                statinfo.getSelectionModel().select(0);
                getValues();
                this.setCenter(createChart());
                addStats();
                this.setBottom(hbox1);

            }
        });

        hbox.setStyle("-fx-background-color:#D81E05");

        this.setCenter(vbox);
        this.setTop(hbox);

        hbox.getChildren().addAll(caption);
        vbox.getChildren().addAll(dateChoose, label1, date1, label2, date2, submitDate);

        date1.setPromptText("ex. 2016");
        date1.setMaxWidth(75);
        date1.setAlignment(Pos.CENTER);
        date2.setPromptText("ex. 2017");
        date2.setMaxWidth(75);
        date2.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10, 50, 50, 50));

        dateChoose.setAlignment(Pos.CENTER);

        hbox.setAlignment(Pos.CENTER);
        vbox.setAlignment(Pos.CENTER);

    }

    //de methode voor de back, submit en combobox
    public void addStats() {

        hbox1.setAlignment(Pos.CENTER);
        hbox1.getChildren().addAll(statinfo, chooseinfo, back);

        //de submit knop voor de combobox
        chooseinfo.setOnAction(e -> {
            this.getChildren().removeAll(getCenter(), getRight(), getLeft());
            if (statinfo.getSelectionModel().getSelectedItem() == "Pie chart") {
                this.setCenter(createChart());
            } else if (statinfo.getSelectionModel().getSelectedItem() == "Graph") {
                getValuesGraph();
                this.setCenter(createGraph());
            }
        });

        //de back knop backend, na klik ga terug naar input scherm
        back.setOnAction(e -> {
            this.getChildren().clear();
            hbox.getChildren().clear();
            vbox.getChildren().clear();
            hbox1.getChildren().clear();
            this.initScreen(primaryStage);
        });
    }
}
