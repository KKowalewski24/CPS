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
    private CustomTab ACConversionTab;
    private CustomTab CAConversionTab;

    /*------------------------ METHODS REGION ------------------------*/
    public CustomTabPane(CustomTab chartTab, CustomTab histogramTab,
                         CustomTab paramsTab, CustomTab ACConversionTab,
                         CustomTab CAConversionTab) {
        super(chartTab, histogramTab, paramsTab, ACConversionTab, CAConversionTab);
        this.chartTab = chartTab;
        this.histogramTab = histogramTab;
        this.paramsTab = paramsTab;
        this.ACConversionTab = ACConversionTab;
        this.CAConversionTab = CAConversionTab;
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

    public CustomTab getACConversionTab() {
        return ACConversionTab;
    }

    public void setACConversionTab(CustomTab ACConversionTab) {
        this.ACConversionTab = ACConversionTab;
    }

    public CustomTab getCAConversionTab() {
        return CAConversionTab;
    }

    public void setCAConversionTab(CustomTab CAConversionTab) {
        this.CAConversionTab = CAConversionTab;
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
                .append(ACConversionTab, that.ACConversionTab)
                .append(CAConversionTab, that.CAConversionTab)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(chartTab)
                .append(histogramTab)
                .append(paramsTab)
                .append(ACConversionTab)
                .append(CAConversionTab)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("chartTab", chartTab)
                .append("histogramTab", histogramTab)
                .append("paramsTab", paramsTab)
                .append("ACConversionTab", ACConversionTab)
                .append("CAConversionTab", CAConversionTab)
                .toString();
    }
}
    