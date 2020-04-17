package pl.jkkk.cps.view.fxml.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WindowDimensions {

    /*------------------------ FIELDS REGION ------------------------*/
    private double width;
    private double height;

    /*------------------------ METHODS REGION ------------------------*/
    public WindowDimensions(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WindowDimensions that = (WindowDimensions) o;

        return new EqualsBuilder()
                .append(width, that.width)
                .append(height, that.height)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(width)
                .append(height)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("width", width)
                .append("height", height)
                .toString();
    }
}
    