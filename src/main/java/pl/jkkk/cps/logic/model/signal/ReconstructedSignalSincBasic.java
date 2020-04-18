package pl.jkkk.cps.logic.model.signal;

public class ReconstructedSignalSincBasic extends ContinuousSignal {

    private final DiscreteSignal sourceSignal;
    private final int N;

    public ReconstructedSignalSincBasic(DiscreteSignal sourceSignal, int N) {
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
        this.sourceSignal = sourceSignal;
        this.N = N;
    }

    @Override
    public double value(double t) {

        /* find nearest sample */
        int index = (int) Math.floor((t - getRangeStart())
                / getRangeLength() * sourceSignal.getNumberOfSamples());

        /* find range of N (or less) samples */
        int firstSample = index - N / 2;
        int lastSample = firstSample + N;
        if (firstSample < 0) {
            lastSample = lastSample - firstSample;
            firstSample = 0;
            if (lastSample > sourceSignal.getNumberOfSamples()) {
                lastSample = sourceSignal.getNumberOfSamples();
            }
        } else if (lastSample > sourceSignal.getNumberOfSamples()) {
            firstSample = firstSample - (lastSample - sourceSignal.getNumberOfSamples());
            lastSample = sourceSignal.getNumberOfSamples();
            if (firstSample < 0) {
                firstSample = 0;
            }
        }

        /* calculate value */
        final double step = getRangeLength() / sourceSignal.getNumberOfSamples();
        double sum = 0.0;
        for (int i = firstSample; i < lastSample; i++) {
            sum += sourceSignal.value(i) * sinc(t / step - i);
        }

        return sum;
    }

    private double sinc(double t) {
        if (t == 0.0) {
            return 1.0;
        } else {
            return Math.sin(Math.PI * t) / (Math.PI * t);
        }
    }
}
