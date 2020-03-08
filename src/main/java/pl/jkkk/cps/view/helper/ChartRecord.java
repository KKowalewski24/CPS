package pl.jkkk.cps.view.helper;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class ChartRecord<T1, T2> {

    /*------------------------ FIELDS REGION ------------------------*/
    private T1 axisX;
    private T2 axisY;

    /*------------------------ METHODS REGION ------------------------*/
    public ChartRecord(T1 axisX, T2 axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
    }

    public T1 getAxisX() {
        return axisX;
    }

    public void setAxisX(T1 axisX) {
        this.axisX = axisX;
    }

    public T2 getAxisY() {
        return axisY;
    }

    public void setAxisY(T2 axisY) {
        this.axisY = axisY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChartRecord<?, ?> that = (ChartRecord<?, ?>) o;

        return new EqualsBuilder()
                .append(axisX, that.axisX)
                .append(axisY, that.axisY)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(axisX)
                .append(axisY)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("axisX", axisX)
                .append("axisY", axisY)
                .toString();
    }
}
    