package pl.jkkk.cps.logic.model.signal;

import pl.jkkk.cps.logic.model.data.Data;

import java.util.List;

public abstract class ContinuousSignal extends Signal {

    public ContinuousSignal(double rangeStart, double rangeLength) {
        super(rangeStart, rangeLength);
    }

    public abstract double value(double t);

    @Override
    public List<Data> generateDiscreteRepresentation() {
        return new ContinuousBasedDiscreteSignal(getRangeStart(), getRangeLength(),
                1000000.0 / getRangeLength(), this)
                .generateDiscreteRepresentation();
    }
}
