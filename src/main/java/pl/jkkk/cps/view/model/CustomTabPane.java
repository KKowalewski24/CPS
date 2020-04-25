package pl.jkkk.cps.view.model;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CustomTabPane extends TabPane {

    /*------------------------ FIELDS REGION ------------------------*/
    private Tab chartTab;
    private Tab histogramTab;
    private Tab paramsTab;

    /*------------------------ METHODS REGION ------------------------*/
    public CustomTabPane(Tab chartTab, Tab histogramTab, Tab paramsTab) {
        super(chartTab, histogramTab, paramsTab);
        this.chartTab = chartTab;
        this.histogramTab = histogramTab;
        this.paramsTab = paramsTab;
    }

    public Tab getChartTab() {
        return chartTab;
    }

    public void setChartTab(Tab chartTab) {
        this.chartTab = chartTab;
    }

    public Tab getHistogramTab() {
        return histogramTab;
    }

    public void setHistogramTab(Tab histogramTab) {
        this.histogramTab = histogramTab;
    }

    public Tab getParamsTab() {
        return paramsTab;
    }

    public void setParamsTab(Tab paramsTab) {
        this.paramsTab = paramsTab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomTabPane that = (CustomTabPane) o;

        return new EqualsBuilder()
                .append(chartTab, that.chartTab)
                .append(histogramTab, that.histogramTab)
                .append(paramsTab, that.paramsTab)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(chartTab)
                .append(histogramTab)
                .append(paramsTab)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("chartTab", chartTab)
                .append("histogramTab", histogramTab)
                .append("paramsTab", paramsTab)
                .toString();
    }
}
    