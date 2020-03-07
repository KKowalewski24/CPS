package pl.jkkk.cps.view.helper;

import javafx.scene.control.TabPane;

public class CustomTabPane extends TabPane {

    /*------------------------ FIELDS REGION ------------------------*/
    private CustomTab chartTab;
    private CustomTab histogramTab;
    private CustomTab paramsTab;

    /*------------------------ METHODS REGION ------------------------*/
    public CustomTabPane(CustomTab chartTab, CustomTab histogramTab, CustomTab paramsTab) {
        super(chartTab, histogramTab, paramsTab);
        this.chartTab = chartTab;
        this.histogramTab = histogramTab;
        this.paramsTab = paramsTab;
    }

    public CustomTab getChartTab() {
        return chartTab;
    }

    public void setChartTab(CustomTab chartTab) {
        this.chartTab = chartTab;
    }

    public CustomTab getHistogramTab() {
        return histogramTab;
    }

    public void setHistogramTab(CustomTab histogramTab) {
        this.histogramTab = histogramTab;
    }

    public CustomTab getParamsTab() {
        return paramsTab;
    }

    public void setParamsTab(CustomTab paramsTab) {
        this.paramsTab = paramsTab;
    }
}
    