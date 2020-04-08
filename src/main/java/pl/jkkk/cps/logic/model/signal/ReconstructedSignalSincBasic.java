package pl.jkkk.cps.logic.model.signal;

import java.util.List;

import pl.jkkk.cps.logic.model.Data;

public class ReconstructedSignalSincBasic extends ContinuousSignal{

    private final List<Data> sourceData;
    private final int N;

    public ReconstructedSignalSincBasic(Signal sourceSignal, int N){
        super(sourceSignal.getRangeStart(), sourceSignal.getRangeLength());
        this.sourceData = sourceSignal.getData();
        this.N = N;
    }

    @Override
    public double value(double t) {

        /* find nearest sample */
        int index = (int) Math.floor((t - rangeStart) / rangeLength * sourceData.size());
        
        /* find range of N (or less) samples */
        int firstSample = index - N / 2;
        int lastSample = firstSample + N;
        if (firstSample < 0) {
            lastSample = lastSample - firstSample;
            firstSample = 0;
            if (lastSample > sourceData.size()) {
                lastSample = sourceData.size();
            }
        } else if (lastSample > sourceData.size()) {
            firstSample = firstSample - (lastSample - sourceData.size());
            lastSample = sourceData.size();
            if (firstSample < 0) {
                firstSample = 0;
            }
        }
        
        /* calculate value */
        final double step = rangeLength / sourceData.size();
        double sum = 0.0;
        for (int i = firstSample; i < lastSample; i++) {
            sum += sourceData.get(i).getY() * sinc(t / step - i);
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
