package pl.jkkk.cps.view.fxml;

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
import javafx.scene.layout.Pane;
import pl.jkkk.cps.view.model.ChartRecord;
import pl.jkkk.cps.view.model.CustomTabPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class FxHelper {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    private FxHelper() {
    }

    /*--------------------------------------------------------------------------------------------*/
    public static void changeTheme(String pathPanel, String title, String pathCss) {
        if (StageController.getGlobalCssStyling() != null) {
            StageController.setGlobalCssStyling(null);
            StageController.reloadStage(pathPanel, title);
        } else {
            StageController.setGlobalCssStyling(pathCss);
            StageController.reloadStage(pathPanel, title);
        }
    }

    /*--------------------------------------------------------------------------------------------*/
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

    /*--------------------------------------------------------------------------------------------*/
    public static CustomTabPane getCurrentCustomTabPaneFromTabPane(TabPane tabPane) {
        return (CustomTabPane) tabPane.getSelectionModel().getSelectedItem().getContent();
    }

    public static void removeAndAddNewPaneChildren(Pane pane, Node... nodes) {
        pane.getChildren().clear();
        pane.getChildren().addAll(nodes);
    }

    public static void textFieldSetEditable(boolean value, TextField... textFields) {
        Arrays.stream(textFields).forEach((it) -> it.setEditable(value));
    }

    public static Double getTextFieldValueToDouble(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    public static Integer getTextFieldValueToInteger(TextField textField) {
        return Integer.parseInt(textField.getText());
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
        comboBox.getItems().clear();
        collection.forEach((it) -> comboBox.getItems().add(it));
        comboBox.getSelectionModel().selectFirst();
    }

    public static String getValueFromComboBox(ComboBox comboBox) {
        return comboBox.getSelectionModel().getSelectedItem().toString();
    }

    public static Integer getIndexFromComboBox(ComboBox comboBox) {
        return comboBox.getSelectionModel().getSelectedIndex();
    }

    public static Integer getSelectedTabIndex(TabPane tabPane) {
        return tabPane.getSelectionModel().getSelectedIndex();
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

    public static void addNewDataToChart(XYChart chart, XYChart.Series series) {
        chart.getData().add(series);
    }

    /*--------------------------------------------------------------------------------------------*/
    public static void updateNumberAxis(NumberAxis numberAxis, double lowerBound,
                                        double upperBound, double tickUnit) {
        numberAxis.setAutoRanging(false);
        numberAxis.setLowerBound(lowerBound);
        numberAxis.setUpperBound(upperBound);
        numberAxis.setTickUnit(tickUnit);
    }

    public static LineChart prepareLineChart(String... title) {
        LineChart lineChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);

        if (title.length == 1) {
            lineChart.setTitle(title[0]);
        }

        return lineChart;
    }

    public static void prepareLineChart(LineChart lineChart) {
        lineChart.setCreateSymbols(false);
        lineChart.setAnimated(false);
    }

    public static BarChart prepareBarChart(String... title) {
        BarChart barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        barChart.setAnimated(false);

        if (title.length == 1) {
            barChart.setTitle(title[0]);
        }

        return barChart;
    }

    public static ScatterChart prepareScatterChart(String... title) {
        ScatterChart scatterChart = new ScatterChart(new NumberAxis(), new NumberAxis());
        scatterChart.setAnimated(false);

        if (title.length == 1) {
            scatterChart.setTitle(title[0]);
        }

        return scatterChart;
    }

    /*--------------------------------------------------------------------------------------------*/
    public static void switchTabToAnother(TabPane tabPane, int index) {
        tabPane.getSelectionModel().select(index);
    }

    public static void changeLineChartToScatterChart(TabPane tabPane) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);
        customTabPane.getChartTab().setContent(prepareScatterChart());
    }

    public static void changeLineChartToScatterChart(TabPane tabPane, ScatterChart scatterChart) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);
        customTabPane.getChartTab().setContent(scatterChart);
    }

    public static void changeScatterChartToLineChart(TabPane tabPane) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);
        customTabPane.getChartTab().setContent(prepareLineChart());
    }

    public static void changeScatterChartToLineChart(TabPane tabPane, LineChart lineChart) {
        CustomTabPane customTabPane = getCurrentCustomTabPaneFromTabPane(tabPane);
        customTabPane.getChartTab().setContent(lineChart);
    }

    /*--------------------------------------------------------------------------------------------*/
    public static void clearAndFillLineChart(LineChart lineChart,
                                             Collection<ChartRecord<Number, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(lineChart, series);
    }

    public static void fillLineChart(LineChart lineChart,
                                     Collection<ChartRecord<Number, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        addNewDataToChart(lineChart, series);
    }

    public static void clearAndFillScatterChart(ScatterChart scatterChart,
                                                Collection<ChartRecord<Number, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(scatterChart, series);
    }

    public static void fillScatterChart(ScatterChart scatterChart,
                                        Collection<ChartRecord<Number, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        addNewDataToChart(scatterChart, series);
    }

    public static void clearAndFillBarChart(BarChart barChart,
                                            Collection<ChartRecord<String, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        clearAndAddNewDataToChart(barChart, series);
    }

    public static void fillBarChart(BarChart barChart,
                                    Collection<ChartRecord<String, Number>> dataCollection) {
        XYChart.Series series = new XYChart.Series<>();

        dataCollection.forEach((it) -> {
            series.getData().add(prepareDataRecord(it.getX(), it.getY()));
        });

        addNewDataToChart(barChart, series);
    }
}
