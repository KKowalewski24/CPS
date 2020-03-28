package pl.jkkk.cps.logic.model;

import pl.jkkk.cps.logic.model.signal.ContinuousSignal;
import pl.jkkk.cps.logic.model.signal.Signal;

public class DAC {

    public ContinuousSignal zeroOrderHold(Signal signal) {
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()) {
            @Override
            public double value(double t) {
                int index =
                        (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength()
                                * (signal.getData().size() - 1));
                return signal.getData().get(index).getY();
            }
        };
    }

    public ContinuousSignal firstOrderHold(Signal signal) {
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()) {
            @Override
            public double value(double t) {
                int index =
                        (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength()
                                * (signal.getData().size() - 1));
                Data A = signal.getData().get(index);
                if (index < signal.getData().size() - 1) {
                    Data B = signal.getData().get(index + 1);
                    return (t - A.getX()) / (B.getX() - A.getX()) * (B.getY() - A.getY()) + A.getY();
                } else {
                    return A.getY();
                }
            }
        };
    }
    
    public ContinuousSignal sincBasic(Signal signal, int N) {
        return new ContinuousSignal(signal.getRangeStart(), signal.getRangeLength()) {
            @Override
            public double value(double t) {
                /* find nearest sample */
                int index = (int) Math.floor((t - signal.getRangeStart()) / signal.getRangeLength()
                                * (signal.getData().size() - 1));
                /* find range of N (or less) samples */
                int firstSample = index - N / 2;
                int lastSample = firstSample + N;
                if(firstSample < 0){
                    lastSample = lastSample - firstSample;
                    firstSample = 0;
                    if(lastSample > signal.getData().size())
                        lastSample = signal.getData().size();
                }else if(lastSample > signal.getData().size()){
                    firstSample = firstSample - (lastSample - signal.getData().size());
                    lastSample = signal.getData().size();
                    if(firstSample < 0)
                        firstSample = 0;
                }
                /* calculate value */
                final double step = signal.getRangeLength() / (signal.getData().size() - 1);
                double sum = 0.0;
                for(int i = firstSample; i < lastSample; i++){
                    sum += signal.getData().get(i).getY() * sinc(t / step - i);
                }
                return sum;
            }
        };
    }

    private double sinc(double t){
        if (t == 0.0)
            return 1.0;
        else
            return Math.sin(Math.PI * t) / (Math.PI * t);
    }
}
