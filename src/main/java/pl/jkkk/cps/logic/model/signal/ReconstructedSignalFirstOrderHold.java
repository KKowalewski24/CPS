package pl.jkkk.cps.logic.model.signal;

public class ReconstructedSignalFirstOrderHold extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;

    public ReconstructedSignalFirstOrderHold(DiscreteSignal sourceSignal) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
        this.sourceSignal = sourceSignal;
    }

    @Override
    public double value(double t) {
        int index = (int) Math.floor((t - getRangeStart())
                / getRangeLength() * sourceSignal.getNumberOfSamples());

        if (index < sourceSignal.getNumberOfSamples() - 1) {
            return (t - sourceSignal.argument(index))
                    / (sourceSignal.argument(index + 1) - sourceSignal.argument(index))
                    * (sourceSignal.value(index + 1) - sourceSignal.value(index))
                    + sourceSignal.value(index);
        } else {
            return sourceSignal.value(index);
        }
    }
}
