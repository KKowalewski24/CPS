package pl.jkkk.cps.logic.model.signal;

public abstract class ComplexSignal extends Signal {

    public enum DiscreteRepresentationType {
        ABS,
        REAL,
        IMAGINARY,
        ARG
    }

    private DiscreteRepresentationType discreteRepresentationType;

    public ComplexSignal(final double rangeStart, final double rangeLength) {
        super(rangeStart, rangeLength);
    }

    public DiscreteRepresentationType getDiscreteRepresentationType() {
        return discreteRepresentationType;
    }

    public void setDiscreteRepresentationType(DiscreteRepresentationType discreteRepresentationType) {
        this.discreteRepresentationType = discreteRepresentationType;
    }
}
