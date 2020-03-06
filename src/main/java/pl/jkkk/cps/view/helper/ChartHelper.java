package pl.jkkk.cps.view.helper;

import javafx.scene.chart.XYChart;

public class ChartHelper {

    /*------------------------ FIELDS REGION ------------------------*/

    /*------------------------ METHODS REGION ------------------------*/
    private ChartHelper() {
    }

    public static XYChart.Data prepareDataRecord(String string, Number number) {
        return new XYChart.Data(string, number);
    }
}
    