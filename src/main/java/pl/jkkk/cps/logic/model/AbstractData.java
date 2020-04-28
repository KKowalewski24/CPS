package pl.jkkk.cps.logic.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class AbstractData<T> implements Serializable {

    /*------------------------ FIELDS REGION ------------------------*/
    private double x;
    private T y;

    /*------------------------ METHODS REGION ------------------------*/
    public AbstractData(double x, T y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AbstractData data = (AbstractData) o;

        return new EqualsBuilder()
                .append(x, data.x)
                .append(y, data.y)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(x)
                .append(y)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("x", x)
                .append("y", y)
                .toString();
    }
}
    
