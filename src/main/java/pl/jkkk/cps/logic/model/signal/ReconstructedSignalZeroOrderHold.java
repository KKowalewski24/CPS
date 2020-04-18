package pl.jkkk.cps.logic.model.signal;

public class ReconstructedSignalZeroOrderHold extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;

    public ReconstructedSignalZeroOrderHold(DiscreteSignal sourceSignal) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
        this.sourceSignal = sourceSignal;
    }

    public double value(double t) {
        int index = (int) Math.floor((t - getRangeStart())
                / getRangeLength() * sourceSignal.getNumberOfSamples());
        return sourceSignal.value(index);
    }
}
