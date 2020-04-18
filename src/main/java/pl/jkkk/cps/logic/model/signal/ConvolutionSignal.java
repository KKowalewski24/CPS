package pl.jkkk.cps.logic.model.signal;

public class ConvolutionSignal extends DiscreteSignal {

    private final DiscreteSignal h;
    private final DiscreteSignal x;

    public ConvolutionSignal(DiscreteSignal h, DiscreteSignal x) {
        super(h.getRangeStart(),
                h.getNumberOfSamples() + x.getNumberOfSamples() - 1,
                h.getSampleRate());
        this.h = h;
        this.x = x;
    }

    @Override
    public double value(int i) {
        double sum = 0.0;
        int startK = Math.max(0, i - x.getNumberOfSamples() + 1);
        int endK = Math.min(h.getNumberOfSamples(), i + 1);
        for (int k = startK; k < endK; k++) {
            sum += h.value(k) * x.value(i - k);
        }

        return sum;
    }
}
