package pl.jkkk.cps.view.helper;

import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChartHelper {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    private ChartHelper() {
    }

    public static XYChart.Data<Number, Number> prepareDataRecord(Number numberOne,
                                                                 Number numberTwo) {
        return new XYChart.Data(numberOne, numberTwo);
    }

    public static XYChart.Data<Number, String> prepareDataRecord(Number number,
                                                                 String string) {
        return new XYChart.Data(number, string);
    }

    public static XYChart.Data<String, Number> prepareDataRecord(String string,
                                                                 Number number) {
        return new XYChart.Data(string, number);
    }

    public static XYChart.Data<String, String> prepareDataRecord(String stringOne,
                                                                 String stringTwo) {
        return new XYChart.Data(stringOne, stringTwo);
    }

    public static CustomTabPane castTabPaneToCustomTabPane(TabPane tabPane) {
        return (CustomTabPane) tabPane.getSelectionModel().getSelectedItem().getContent();
    }

    public static void textFieldSetValue(TextField textField, String string) {
        textField.setText(string);
    }

    public static TextField setTextFieldPosition(TextField textField,
                                                 int pointX, int pointY) {
        textField.setLayoutX(pointX);
        textField.setLayoutY(pointY);

        return textField;
    }

    public static Label prepareLabelWithPosition(String text, int pointX, int pointY) {
        Label label = new Label(text);
        label.setLayoutX(pointX);
        label.setLayoutY(pointY);

        return label;
    }

    public static void appendLabelText(Node node, String text) {
        Label label = (Label) node;
        String initialText = label.getText().substring(0, label.getText().indexOf(":") + 1);
        label.setText(initialText + "     " + text);
    }

    public static void fillComboBox(ComboBox comboBox, Collection collection) {
        List items = comboBox.getItems();
        items.clear();
        collection.forEach((it) -> items.add(it));
        comboBox.getSelectionModel().selectFirst();
    }

    public static List<String> getTabNameList(List<Tab> tabList) {
        List<String> names = new ArrayList<>();
        tabList.forEach((it) -> names.add(it.getText()));

        return names;
    }

    public static void clearAndAddNewDataToChart(XYChart chart, XYChart.Series series) {
        chart.getData().clear();
        chart.getData().add(series);
    }

    public static LineChart prepareLineChart() {
        LineChart lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        return lineChart;
    }

    public static BarChart prepareBarChart() {
        BarChart barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setAnimated(false);

        return barChart;
    }

    public static ScatterChart prepareScatterChart() {
        ScatterChart scatterChart = new ScatterChart(new NumberAxis(), new NumberAxis());
        scatterChart.setAnimated(false);

        return scatterChart;
    }

    public static void changeLineChartToScatterChart(TabPane tabPane) {
        CustomTabPane customTabPane = castTabPaneToCustomTabPane(tabPane);
        customTabPane.getChartTab().setContent(prepareScatterChart());
    }

    public static void changeScatterChartToLineChart(TabPane tabPane) {
        CustomTabPane customTabPane = castTabPaneToCustomTabPane(tabPane);
        customTabPane.getChartTab().setContent(prepareLineChart());
    }

    public static void fillLineChart(CustomTabPane customTabPane,
                                     Collection<ChartRecord<Number, Number>> dataCollection) {
        LineChart lineChart = (LineChart) customTabPane.getChartTab().getContent();
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(lineChart, series);
    }

    public static void fillScatterChart(CustomTabPane customTabPane,
                                        Collection<ChartRecord<Number, Number>> dataCollection) {
        ScatterChart scatterChart = (ScatterChart) customTabPane.getChartTab().getContent();
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(scatterChart, series);
    }

    public static void fillBarChart(CustomTabPane customTabPane,
                                    Collection<ChartRecord<String, Number>> dataCollection) {
        BarChart barChart = (BarChart) customTabPane.getHistogramTab().getContent();
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(barChart, series);
    }

}
    
