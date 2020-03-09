package pl.jkkk.cps.view.helper;

import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.Collection;

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

    public static void textFieldSetValue(TextField textField, String string) {
        textField.setText(string);
    }

    public static void fillComboBox(ComboBox comboBox, Collection collection) {
        collection.forEach((it) -> comboBox.getItems().add(it));
        comboBox.getSelectionModel().selectFirst();
    }
}
    