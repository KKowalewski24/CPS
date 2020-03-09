package pl.jkkk.cps.view.helper;

import javafx.scene.control.TabPane;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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

    public CustomTab getHistogramTab() {
        return histogramTab;
    }

    public CustomTab getParamsTab() {
        return paramsTab;
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
    